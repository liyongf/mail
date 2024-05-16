require('../../../../src/openlayers/overlay/theme/themeFeature');

describe('openlayers_testthemeFeature', function () {
    var originalTimeout, map;
    beforeAll(function () {
        testDiv = window.document.createElement("div");
        testDiv.setAttribute("id", "map");
        testDiv.style.styleFloat = "left";
        testDiv.style.marginLeft = "8px";
        testDiv.style.marginTop = "50px";
        testDiv.style.width = "500px";
        testDiv.style.height = "500px";
        window.document.body.appendChild(testDiv);

        var baseUrl = GlobeParameter.jingjinMapURL + "/maps/京津地区地图",
            extent = [104.07, 30.54, 119.51, 42.31];
        map = new ol.Map({
            target: 'map',
            controls: ol.control.defaults({attributionOptions: {collapsed: false}})
                .extend([new ol.supermap.control.Logo()]),
            view: new ol.View({
                center: [116.85, 39.79],
                zoom: 8,
                projection: "EPSG:4326",
                extent: extent
            })
        });
        var layer = new ol.layer.Tile({
            source: new ol.source.TileSuperMapRest({
                url: baseUrl
            })
        });
        map.addLayer(layer);
    });


    beforeEach(function () {
        originalTimeout = jasmine.DEFAULT_TIMEOUT_INTERVAL;
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 50000;
    });

    afterEach(function () {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = originalTimeout;
    });

    afterAll(function () {
        window.document.body.removeChild(testDiv);
        map.remove();
    });


    it("toFeature", function () {
        var geo = new ol.geom.Point([116.407283, 39.904557]);
        var attrs = {};
        attrs.NAME = "北京市";
        attrs.CON2009 = 22023;
        var themeFeature = new ol.supermap.ThemeFeature(geo, attrs);
        var result = themeFeature.toFeature();

        expect(result).not.toBeNull();
        expect(result instanceof SuperMap.Feature.Vector).toBeTruthy();
        expect(result.geometry).not.toBeNull();
        expect(result.attributes).not.toBeNull();
        expect(result.attributes.NAME).toBe('北京市');
        expect(result.attributes.CON2009).toBe(22023);
        expect(result.geometry.x).toBe(116.407283);
        expect(result.geometry.y).toBe(39.904557);
    });

});


