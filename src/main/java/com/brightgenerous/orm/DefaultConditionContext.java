package com.brightgenerous.orm;

public class DefaultConditionContext extends AbstractConditionContext {

    private static final long serialVersionUID = 6345067996061492274L;

    private static volatile DefaultConditionContext instance;

    public static DefaultConditionContext get() {
        if (instance == null) {
            synchronized (DefaultConditionContext.class) {
                if (instance == null) {
                    instance = new DefaultConditionContext();
                }
            }
        }
        return instance;
    }

    protected DefaultConditionContext() {
    }

    @Override
    public Condition newCondition(UpdatedCallback uc) {
        return new Condition(this, uc);
    }
}
