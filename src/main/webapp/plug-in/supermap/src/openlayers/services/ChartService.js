import ol from 'openlayers/dist/ol-debug';
import SuperMap from '../../common/SuperMap';
import ServiceBase from './ServiceBase';
import ChartQueryService from '../../common/iServer/ChartQueryService';
import ChartFeatureInfoSpecsService from '../../common/iServer/ChartFeatureInfoSpecsService';

/**
 * @class ol.supermap.ChartService
 * @classdesc 海图服务。
 * @extends ol.supermap.ServiceBase
 * @example
 *      new ol.supermap.ChartService(url)
 *      .queryChart(param,function(result){
 *          //doSomething
 *      })
 * @param url - {string} 与客户端交互的海图服务地址。
 * @param options -{Object} 交互时所需可选参数。
 * 
 */
export default class ChartService extends ServiceBase {

    constructor(url, options) {
        super(url, options);
    }

    /**
     * @function ol.supermap.ChartService.prototype.queryChart
     * @description 查询海图服务。
     * @param params - {SuperMap.ChartQueryParameters} 海图查询所需参数类。
     * @param callback - {function} 回调函数。
     * @param resultFormat - {SuperMap.DataFormat} 返回的结果格式类型。
     * @return {ol.supermap.ChartService}
     */
    queryChart(params, callback, resultFormat) {
        var me = this,
            param = me._processParams(params),
            format = me._processFormat(resultFormat);
        var chartQueryService = new ChartQueryService(me.url, {
            serverType: me.options.serverType,
            eventListeners: {
                scope: me,
                processCompleted: callback,
                processFailed: callback
            },
            format: format
        });

        chartQueryService.processAsync(param);
        return me;
    }

    /**
     * @function ol.supermap.ChartService.prototype.getChartFeatureInfo
     * @description 获取海图物标信息服务。
     * @param callback - {function} 回调函数
     * @return {ol.supermap.ChartService}
     */
    getChartFeatureInfo(callback) {
        var me = this, url = me.url.concat();
        url += "/chartFeatureInfoSpecs";
        var chartFeatureInfoSpecsService = new ChartFeatureInfoSpecsService(url, {
            serverType: me.options.serverType,
            eventListeners: {
                scope: me,
                processCompleted: callback,
                processFailed: callback
            }
        });
        chartFeatureInfoSpecsService.processAsync();
        return me;
    }

    _processParams(params) {
        if (!params) {
            return {};
        }
        params.returnContent = (params.returnContent == null) ? true : params.returnContent;
        if (params.filter) {
            params.chartQueryFilterParameters = ol.supermap.Util.isArray(params.filter) ? params.filter : [params.filter];
        }
        if (params.bounds) {
            params.bounds = new SuperMap.Bounds(
                params.bounds[0],
                params.bounds[1],
                params.bounds[2],
                params.bounds[3]
            );
        }
    }

    _processFormat(resultFormat) {
        return (resultFormat) ? resultFormat : SuperMap.DataFormat.GEOJSON;
    }
}

ol.supermap.ChartService = ChartService;