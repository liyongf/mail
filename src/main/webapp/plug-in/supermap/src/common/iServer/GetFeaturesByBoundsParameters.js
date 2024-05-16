import SuperMap from '../SuperMap';
import {SpatialQueryMode} from '../REST';
import FilterParameter from './FilterParameter';
import GetFeaturesParametersBase from './GetFeaturesParametersBase';

/**
 * @class SuperMap.GetFeaturesByBoundsParameters
 * @classdesc 数据集范围查询参数类,该类用于设置数据集范围查询的相关参数。
 * @param options - {Object} 可选参数。如：<br>
 *        bounds - {Object} 用于查询的范围对象。Bounds类型可以是SuperMap.Bounds|L.Bounds|ol.extent。</br>
 *        attributeFilter - {string} 范围查询属性过滤条件。</br>
 *        fields - {Array<string>} 设置查询结果返回字段。默认返回所有字段。</br>
 *        spatialQueryMode - {SuperMap.SpatialQueryMode} 空间查询模式常量,必设参数。</br>
 *        queryParameter - {@link SuperMap.FilterParameter} 查询过滤条件参数。</br>
 *        datasetNames - {Array<string>} 数据集集合中的数据集名称列表。</br>
 *        returnContent - {boolean} 是否直接返回查询结果。</br>
 *        fromIndex - {integer} 查询结果的最小索引号。</br>
 *        toIndex - {integer} 查询结果的最大索引号。</br>
 * @extends SuperMap.GetFeaturesParametersBase
 */

export default class GetFeaturesByBoundsParameters extends GetFeaturesParametersBase {
    /**
     * @member SuperMap.GetFeaturesByBoundsParameters.prototype.getFeatureMode - {string}
     * @description 数据集查询模式。范围查询有"BOUNDS"，"BOUNDS_ATTRIBUTEFILTER"两种,当用户设置attributeFilter时会自动切换到BOUNDS_ATTRIBUTEFILTER访问服务。
     */
    getFeatureMode = null;

    /**
     * @member SuperMap.GetFeaturesByBoundsParameters.prototype.bounds - {Object}
     * @description 用于查询的范围对象。Bounds类型可以是SuperMap.Bounds|L.Bounds|ol.extent。
     *
     */
    bounds = null;

    /**
     * @member SuperMap.GetFeaturesByBoundsParameters.prototype.fields - {Array<string>}
     * @description 设置查询结果返回字段。当指定了返回结果字段后，则 GetFeaturesResult 中的 features 的属性字段只包含所指定的字段。不设置即返回全部字段。
     */
    fields = null;

    /**
     * @member SuperMap.GetFeaturesByBoundsParameters.prototype.attributeFilter - {string}
     * @description 范围查询属性过滤条件。
     */
    attributeFilter = null;

    /**
     * @member SuperMap.GetFeaturesByBoundsParameters.prototype.spatialQueryMode - {SuperMap.SpatialQueryMode}
     * @description 空间查询模式常量，必设参数，默认为CONTAIN。
     */
    spatialQueryMode = SpatialQueryMode.CONTAIN;

    static  getFeatureMode = {
        "BOUNDS": "BOUNDS",
        "BOUNDS_ATTRIBUTEFILTER": "BOUNDS_ATTRIBUTEFILTER"
    };


    constructor(options) {
        super(options);
        this.getFeatureMode = GetFeaturesByBoundsParameters.getFeatureMode.BOUNDS;
        if (!options) {
            return;
        }
        SuperMap.Util.extend(this, options);
    }

    /**
     * @function SuperMap.GetFeaturesByBoundsParameters.prototype.destroy
     * @override
     */
    destroy() {
        super.destroy();
        var me = this;
        if (me.bounds) {
            me.bounds.destroy();
            me.bounds = null;
        }
        if (me.fields) {
            while (me.fields.length > 0) {
                me.fields.pop();
            }
            me.fields = null;
        }
        me.attributeFilter = null;
        me.spatialQueryMode = null;
        me.getFeatureMode = null;
    }

    /**
     * @function SuperMap.GetFeaturesByBoundsParameters.toJsonParameters
     * @description 将SuperMap.GetFeaturesByBoundsParameters对象参数转换为JSON字符串。
     * @param params - {SuperMap.GetFeaturesByBoundsParameters} 范围查询参数。
     * @return  {string} 转化后的JSON字符串。
     *
     */
    static toJsonParameters(params) {
        var filterParameter,
            bounds,
            parasByBounds;

        bounds = {
            "leftBottom": {"x": params.bounds.left, "y": params.bounds.bottom},
            "rightTop": {"x": params.bounds.right, "y": params.bounds.top}
        };
        parasByBounds = {
            datasetNames: params.datasetNames,
            getFeatureMode: GetFeaturesByBoundsParameters.getFeatureMode.BOUNDS,
            bounds: bounds,
            spatialQueryMode: params.spatialQueryMode
        };
        if (params.fields) {
            filterParameter = new FilterParameter();
            filterParameter.name = params.datasetNames;
            filterParameter.fields = params.fields;
            parasByBounds.queryParameter = filterParameter;
        }
        if (params.attributeFilter) {
            parasByBounds.attributeFilter = params.attributeFilter;
            parasByBounds.getFeatureMode = GetFeaturesByBoundsParameters.getFeatureMode.BOUNDS_ATTRIBUTEFILTER;
        }
        if (params.maxFeatures && !isNaN(params.maxFeatures)) {
            parasByBounds.maxFeatures = params.maxFeatures;
        }

        return SuperMap.Util.toJSON(parasByBounds);
    }

    CLASS_NAME = "SuperMap.GetFeaturesByBoundsParameters"
}

SuperMap.GetFeaturesByBoundsParameters = GetFeaturesByBoundsParameters;