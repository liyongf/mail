import '../core/Base';
import L from "leaflet";

/**
 * @class L.supermap.wmtsLayer
 * @classdesc wmts图层类
 * @extends L.TileLayer{@linkdoc-leaflet/#tilelayer}
 * @param url -{string} wmts图层地址
 * @param options -{Object} wmts图层可选参数。如：<br>
 *        layersID - {number}图层ID，如果有layersID，则是在使用专题图。<br>
 *        redirect - {boolean} 是否从定向，如果为 true，则将请求重定向到图片的真实地址；如果为 false，则响应体中是图片的字节流。<br>
 *        transparent - {number}设置透明度。<br>
 *        cacheEnabled - {string} 启用缓存。<br>
 *        clipRegionEnabled - {boolean} 是否启用地图裁剪。<br>
 *        prjCoordSys - {Object} 请求的地图的坐标参考系统。 如：prjCoordSys={"epsgCode":3857}。<br>
 *        overlapDisplayed - {boolean} 地图对象在同一范围内时，是否重叠显示。<br>
 *        overlapDisplayedOptions - {string} 避免地图对象压盖显示的过滤选项。<br>
 *        tileversion - {string} 切片版本名称，cacheEnabled 为 true 时有效。<br>
 *        crs - {{@link L.Proj.CRS}} 坐标系统类。<br>
 *        serverType - {{@link SuperMap.ServerType}} 服务来源 iServer|iPortal|online。<br>
 *        attribution - {string} 版权信息。
 */
export var WMTSLayer = L.TileLayer.extend({

    options: {
        version: '1.0.0',
        style: '',
        tilematrixSet: '',
        format: 'image/png',
        tileSize: 256,
        matrixIds: null,
        layer: '',
        attribution: "with <span>© <a href='http://iclient.supermapol.com' target='_blank'>SuperMap iClient</a></span>"
    },

    //todo 自动获取Capabilities
    initialize: function (url, options) { // (String, Object)
        this._url = url;
        L.setOptions(this, options);
    },

    /**
     * @function L.supermap.wmtsLayer.prototype.getTileUrl
     * @description 根据行列号获取切片地址
     * @param coords - {Object} 行列号
     * @return {string} 切片地址
     */
    getTileUrl: function (coords) { // (Point, Number) -> String
        var zoom = this._getZoomForUrl();
        var ident = this.options.matrixIds ? this.options.matrixIds[zoom].identifier : zoom;
        var url = L.Util.template(this._url, {s: this._getSubdomain(coords)});
        var obj = {
            service: 'WMTS',
            request: 'GetTile',
            version: this.options.version,
            style: this.options.style,
            tilematrixSet: this.options.tilematrixSet,
            format: this.options.format,
            width: this.options.tileSize,
            height: this.options.tileSize,
            layer: this.options.layer,
            tilematrix: ident,
            tilerow: coords.y,
            tilecol: coords.x
        };
        return url + L.Util.getParamString(obj, url);
    }
});

export var wmtsLayer = function (url, options) {
    return new WMTSLayer(url, options);
};
L.supermap.wmtsLayer = wmtsLayer;