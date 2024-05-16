import L from "leaflet";

/**
 * @class L.supermap.Symbolizer
 * @description 符号类
 * @private
 * @extends L.Class{@linkdoc-leaflet/#class}
 * @param feature — {L.feature} 要素
 */
export var Symbolizer = L.Class.extend({

    initialize: function (feature) {
        this.properties = feature.properties;
        this.type = feature.type;
        this.layerName = feature.layerName;
    },

    /**
     * @function L.supermap.Symbolizer.prototype.render
     * @description 绘制线符号
     * @param renderer - {Object} 渲染器
     * @param style - {string} 符号样式
     */
    render: function (renderer, style) {
        this._renderer = renderer;
        this.options = style;
        renderer._initPath(this);
        renderer._updateStyle(this);
        var elem = this.getElement();
        if (elem && this.layerName) {
            L.DomUtil.addClass(elem, this.layerName);
        }
    },

    /**
     * @function L.supermap.Symbolizer.prototype.updateStyle
     * @description 更新替换符号样式
     * @param renderer - {Object} 渲染器
     * @param style - {string} 符号样式
     */
    updateStyle: function (renderer, style) {
        this.options = style;
        renderer._updateStyle(this);
    },

    /**
     * @function L.supermap.Symbolizer.prototype.getElement
     * @description 获取文本信息
     */
    getElement: function () {
        return this._path || this._renderer._container;
    },

    _getPixelBounds: function () {
        var parts = this._parts;
        var bounds = L.bounds([]);
        for (var i = 0; i < parts.length; i++) {
            var part = parts[i];
            for (var j = 0; j < part.length; j++) {
                bounds.extend(part[j]);
            }
        }

        var w = this._clickTolerance(),
            p = new L.Point(w, w);

        bounds.min._subtract(p);
        bounds.max._add(p);

        return bounds;
    },
    _clickTolerance: L.Path.prototype._clickTolerance
});
