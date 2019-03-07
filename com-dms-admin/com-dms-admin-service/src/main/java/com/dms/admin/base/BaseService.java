package com.dms.admin.base;

import com.google.common.collect.Lists;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author yangchao.
 * @date 2019/3/7 11:31
 */
public class BaseService {

    /**
     * 构建排序参数
     * @param param
     * @return
     */
    protected List<Sort.Order> buildSortParam(BaseParam param) {
        Map<String, String> sorts = param.getSorts();
        List<Sort.Order> orders = Lists.newArrayList();
        sorts.forEach((property, direc) -> {
            Sort.Direction direction = Sort.Direction.fromString(direc);
            Sort.Order order = new Sort.Order(direction, property);
            orders.add(order);
        });
        return orders;
    }

    /**
     * 构造分页排序参数
     * @param param
     * @return
     */
    protected Pageable buildPageParam(BaseParam param) {
        List<Sort.Order> orders = this.buildSortParam(param);
        Pageable pageable = null;
        if (CollectionUtils.isEmpty(orders)) {
            pageable = new PageRequest(param.getPageIndex(), param.getPageSize());
        } else {
            Sort sort = new Sort(orders);
            pageable = new PageRequest(param.getPageIndex(), param.getPageSize(), sort);
        }
        return pageable;
    }
}
