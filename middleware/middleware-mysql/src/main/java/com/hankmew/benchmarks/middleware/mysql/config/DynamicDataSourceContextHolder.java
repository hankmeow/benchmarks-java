package com.hankmew.benchmarks.middleware.mysql.config;

public class DynamicDataSourceContextHolder {

    //每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
    private static final ThreadLocal<DynamicDataSourceEnum> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 注意：使用静态方法setDataSourceId设置当前线程需要使用的数据源id(和当前线程绑定)
     */
    public static void setDataSourceId(final DynamicDataSourceEnum dataSourceEnum) {
        CONTEXT_HOLDER.set(dataSourceEnum);
    }

    /**
     * 获取当前线程使用的数据源id
     */
    public static DynamicDataSourceEnum getDataSourceId() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空当前线程使用的数据源id
     */
    public static void clearDataSourceId() {
        CONTEXT_HOLDER.remove();
    }
}
