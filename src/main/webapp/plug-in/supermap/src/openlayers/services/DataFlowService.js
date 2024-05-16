import ol from 'openlayers/dist/ol-debug';
import SuperMap from '../../common/SuperMap';
import ServiceBase from './ServiceBase';
import DataFlow from '../../common/iServer/DataFlowService';

/**
 * @class ol.supermap.DataFlowService
 * @classdesc 实时数据服务
 * @extends ol.supermap.ServiceBase
 * @example
 *      new ol.supermap.DataFlowService(url)
 *      .queryChart(param,function(result){
 *          //doSomething
 *      })
 * @param url - {string} 与客户端交互的实时数据服务地址。
 * @param options - {Object} 加载实时数据可选参数。如：<br>
 *        style - {function} 设置数据加载样式。<br>
 *        onEachFeature - {function} 设置每个数据加载popup等。<br>
 *        geometry - {Array<Object>} 设置增添的几何要素对象数组。<br>
 *        excludeField - -{Object} 排除字段
 */
export default class DataFlowService extends ServiceBase {

    constructor(url, options) {
        super(url, options);
        options = options || {};
        if (options.projection) {
            this.options.prjCoordSys = new SuperMap.Projection(options.projection);
        }
        ServiceBase.call(this, url, options);
        this.dataFlow = new DataFlow(url, options);
        this.dataFlow.events.on({
            "broadcastSocketConnected": this._defaultEvent,
            "broadcastSocketError": this._defaultEvent,
            "broadcastFailed": this._defaultEvent,
            "broadcastSuccessed": this._defaultEvent,
            "subscribeSocketConnected": this._defaultEvent,
            "subscribeSocketError": this._defaultEvent,
            "messageSuccessed": this._defaultEvent,
            "setFilterParamSuccessed": this._defaultEvent,
            scope: this
        });
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.initBroadcast
     * @description 初始化广播
     * @returns {ol.supermap.DataFlowService}
     */
    initBroadcast() {
        this.dataFlow.initBroadcast();
        return this;
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.broadcast
     * @description 加载广播数据
     * @param obj {JSON} json格式的要素数据
     */
    broadcast(obj) {
        this.dataFlow.broadcast(obj);
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.initSubscribe
     * @description 初始化订阅数据
     * @return {ol.supermap.DataFlowService}
     */
    initSubscribe() {
        this.dataFlow.initSubscribe();
        return this;
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.setPrjCoordSys
     * @description 设置动态投影坐标
     * @param prjCoordSys -{Object} 动态投影参数
     * @return {ol.supermap.DataFlowService}
     */
    setPrjCoordSys(prjCoordSys) {
        if (!prj) {
            return;
        }
        var prj = new SuperMap.Projection(options.projection);
        this.dataFlow.setPrjCoordSys(prj);
        this.options.prjCoordSys = prj;
        return this;
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.setExcludeField
     * @description 设置排除字段
     * @param excludeField - {Object} 排除字段
     * @return {ol.supermap.DataFlowService}
     */
    setExcludeField(excludeField) {
        this.dataFlow.setExcludeField(excludeField);
        this.options.excludeField = excludeField;
        return this;
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.setGeometry
     * @description 设置添加的几何要素数据
     * @param geometry - {Array<Object>} 设置增添的几何要素对象数组。
     * @return {ol.supermap.DataFlowService}
     */
    setGeometry(geometry) {
        this.dataFlow.setGeometry(geometry);
        this.options.geometry = geometry;
        return this;
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.unSubscribe
     * @description 结束订阅数据
     */
    unSubscribe() {
        this.dataFlow.unSubscribe();
    }

    /**
     * @function ol.supermap.DataFlowService.prototype.unBroadcast
     * @description 结束加载广播
     */
    unBroadcast() {
        this.dataFlow.unBroadcast();
    }

    _defaultEvent(e) {
        this.dispatchEvent({type: e.eventType || e.type, value: e});
    }
}
ol.supermap.DataFlowService = DataFlowService;