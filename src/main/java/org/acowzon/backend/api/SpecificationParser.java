package org.acowzon.backend.api;

import org.acowzon.backend.api.request.PageQueryRequest;
import org.acowzon.backend.api.request.QueryItem;
import org.acowzon.backend.enums.QueryPredicateEnum;
import org.acowzon.backend.exception.BusinessException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SpecificationParser {
    public static <EntityType> Specification<EntityType> parseSpecification(PageQueryRequest request, Class<?> DTOClass) throws BusinessException {

        QueryItem[] matchArr = request.getMatchArr();
        return new Specification<EntityType>() {
            final List<Predicate> andPredicateList = new ArrayList<>();
            final List<Predicate> orPredicateList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<EntityType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                for (QueryItem item : matchArr) {
                    Predicate predicate = null;
                    Object[] values = item.getValues();
//                    Field field = null;
//                    try {
//                        field = DTOClass.getField(item.getAttr());
//                    } catch (NoSuchFieldException ignored) {
//                        ;
//                    }
//                    if (field != null) {
//                        Class<?> entityClass = field.getAnnotation(PageQuery.class).translateEntity();
//                        if (entityClass != Void.class) {
//                            for(Object obj : values){
//                                Object ins = null;
//                                try {
//                                    try {
//                                        ins = entityClass.getConstructor().newInstance();
//                                    } catch (InstantiationException e) {
//                                        e.printStackTrace();
//                                    } catch (IllegalAccessException e) {
//                                        e.printStackTrace();
//                                    } catch (InvocationTargetException e) {
//                                        e.printStackTrace();
//                                    }
//                                    BeanUtils.copyProperties(obj,ins);
//                                    obj = ins;
//                                } catch (NoSuchMethodException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
                    switch (item.getMethod()) {
                        case LESS:
                            predicate = criteriaBuilder.lessThan(root.get(item.getAttr()), (Comparable) values[0]);
                            break;
                        case LESS_OR_EQUAL:
                            predicate = criteriaBuilder.lessThanOrEqualTo(root.get(item.getAttr()), (Comparable) values[0]);
                            break;
                        case GREATER:
                            predicate = criteriaBuilder.greaterThan(root.get(item.getAttr()), (Comparable) values[0]);
                            break;
                        case GREATER_OR_EQUAL:
                            predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(item.getAttr()), (Comparable) values[0]);
                            break;
                        case LIKE:
                            predicate = criteriaBuilder.like(root.get(item.getAttr()), "%" + values[0] + "%");
                            break;
                        case EQUAL:
                            predicate = criteriaBuilder.equal(root.get(item.getAttr()), item.getValues()[0]);
                            break;
                        case BETWEEN:
                            predicate = criteriaBuilder.between(root.get(item.getAttr()), (Comparable) values[0], (Comparable) values[1]);
                            break;
                        case NULL:
                            predicate = criteriaBuilder.isNull(root.get(item.getAttr()));
                            break;
                    }
                    if (item.getPredicate() == QueryPredicateEnum.AND && predicate != null) {
                        andPredicateList.add(predicate);
                    } else if (item.getPredicate() == QueryPredicateEnum.OR && predicate != null) {
                        orPredicateList.add(predicate);
                    }
                }
                Predicate[] andResult = andPredicateList.toArray(new Predicate[0]);
                Predicate[] orResult = orPredicateList.toArray(new Predicate[0]);
                return criteriaBuilder.and(andResult);
            }
        };
    }
}
