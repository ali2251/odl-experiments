/*
* Generated file
*
* Generated from: yang module name: monitoring-impl yang module local name: monitoring-impl
* Generated by: org.opendaylight.controller.config.yangjmxgenerator.plugin.JMXGenerator
* Generated at: Sat Jul 15 08:04:32 BST 2017
*
* Do not modify this file unless it is present under src/main directory
*/
package eu.virtuwind.monitoring.impl;
@org.opendaylight.yangtools.yang.binding.annotations.ModuleQName(namespace = "urn:eu:virtuwind:monitoring:impl", name = "monitoring-impl", revision = "2015-07-22")

public abstract class AbstractMonitoringModule extends org.opendaylight.controller.config.spi.AbstractModule<AbstractMonitoringModule> implements eu.virtuwind.monitoring.impl.MonitoringModuleMXBean {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(eu.virtuwind.monitoring.impl.AbstractMonitoringModule.class);

    //attributes start

    public static final org.opendaylight.controller.config.api.JmxAttribute rpcRegistryJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("RpcRegistry");
    private javax.management.ObjectName rpcRegistry; // mandatory

    public static final org.opendaylight.controller.config.api.JmxAttribute dataBrokerJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("DataBroker");
    private javax.management.ObjectName dataBroker; // mandatory

    public static final org.opendaylight.controller.config.api.JmxAttribute notificationServiceJmxAttribute = new org.opendaylight.controller.config.api.JmxAttribute("NotificationService");
    private javax.management.ObjectName notificationService; // optional

    //attributes end

    public AbstractMonitoringModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public AbstractMonitoringModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,org.opendaylight.controller.config.api.DependencyResolver dependencyResolver,AbstractMonitoringModule oldModule,java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void validate() {
        dependencyResolver.validateDependency(org.opendaylight.controller.config.yang.md.sal.binding.RpcProviderRegistryServiceInterface.class, rpcRegistry, rpcRegistryJmxAttribute);
        dependencyResolver.validateDependency(org.opendaylight.controller.config.yang.md.sal.binding.DataBrokerServiceInterface.class, dataBroker, dataBrokerJmxAttribute);
        if(notificationService != null) {
            dependencyResolver.validateDependency(org.opendaylight.controller.config.yang.md.sal.binding.NotificationProviderServiceServiceInterface.class, notificationService, notificationServiceJmxAttribute);
        }

        customValidation();
    }

    protected void customValidation() {
    }

    private org.opendaylight.controller.sal.binding.api.RpcProviderRegistry rpcRegistryDependency;
    protected final org.opendaylight.controller.sal.binding.api.RpcProviderRegistry getRpcRegistryDependency(){
        return rpcRegistryDependency;
    }private org.opendaylight.controller.md.sal.binding.api.DataBroker dataBrokerDependency;
    protected final org.opendaylight.controller.md.sal.binding.api.DataBroker getDataBrokerDependency(){
        return dataBrokerDependency;
    }private org.opendaylight.controller.sal.binding.api.NotificationProviderService notificationServiceDependency;
    protected final org.opendaylight.controller.sal.binding.api.NotificationProviderService getNotificationServiceDependency(){
        return notificationServiceDependency;
    }

    protected final void resolveDependencies() {
        if (notificationService!=null) {
            notificationServiceDependency = dependencyResolver.resolveInstance(org.opendaylight.controller.sal.binding.api.NotificationProviderService.class, notificationService, notificationServiceJmxAttribute);
        }
        rpcRegistryDependency = dependencyResolver.resolveInstance(org.opendaylight.controller.sal.binding.api.RpcProviderRegistry.class, rpcRegistry, rpcRegistryJmxAttribute);
        dataBrokerDependency = dependencyResolver.resolveInstance(org.opendaylight.controller.md.sal.binding.api.DataBroker.class, dataBroker, dataBrokerJmxAttribute);
    }

    public boolean canReuseInstance(AbstractMonitoringModule oldModule){
        // allow reusing of old instance if no parameters was changed
        return isSame(oldModule);
    }

    public java.lang.AutoCloseable reuseInstance(java.lang.AutoCloseable oldInstance){
        // implement if instance reuse should be supported. Override canReuseInstance to change the criteria.
        return oldInstance;
    }

    public boolean isSame(AbstractMonitoringModule other) {
        if (other == null) {
            throw new IllegalArgumentException("Parameter 'other' is null");
        }
        if (!java.util.Objects.deepEquals(rpcRegistry, other.rpcRegistry)) {
            return false;
        }
        if(rpcRegistry!= null) {
            if (!dependencyResolver.canReuseDependency(rpcRegistry, rpcRegistryJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(dataBroker, other.dataBroker)) {
            return false;
        }
        if(dataBroker!= null) {
            if (!dependencyResolver.canReuseDependency(dataBroker, dataBrokerJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }
        if (!java.util.Objects.deepEquals(notificationService, other.notificationService)) {
            return false;
        }
        if(notificationService!= null) {
            if (!dependencyResolver.canReuseDependency(notificationService, notificationServiceJmxAttribute)) { // reference to dependency must be reusable as well
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
        AbstractMonitoringModule that = (AbstractMonitoringModule) o;
        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    // getters and setters
    @Override
    public javax.management.ObjectName getRpcRegistry() {
        return rpcRegistry;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.controller.config.yang.md.sal.binding.RpcProviderRegistryServiceInterface.class)
    public void setRpcRegistry(javax.management.ObjectName rpcRegistry) {
        this.rpcRegistry = rpcRegistry;
    }

    @Override
    public javax.management.ObjectName getDataBroker() {
        return dataBroker;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.controller.config.yang.md.sal.binding.DataBrokerServiceInterface.class)
    public void setDataBroker(javax.management.ObjectName dataBroker) {
        this.dataBroker = dataBroker;
    }

    @Override
    public javax.management.ObjectName getNotificationService() {
        return notificationService;
    }

    @Override
    @org.opendaylight.controller.config.api.annotations.RequireInterface(value = org.opendaylight.controller.config.yang.md.sal.binding.NotificationProviderServiceServiceInterface.class)
    public void setNotificationService(javax.management.ObjectName notificationService) {
        this.notificationService = notificationService;
    }

    public org.slf4j.Logger getLogger() {
        return LOGGER;
    }

}
