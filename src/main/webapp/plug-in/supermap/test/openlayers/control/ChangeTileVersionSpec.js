require('../../../src/openlayers/control/ChangeTileVersion');

describe('openlayers_testChangeTileVersion', function () {
    var map, baseLayer;
    var tileVersions = [
        {
            desc: "V1",
            name: "63477488-e91e-41a1-b029-c1c7b476a64d",
            parent: null,
            timestamp: 1503472107342,
            update: {
                bounds: {
                    bottom: 3.853726078199864,
                    left: 73.45169014635671,
                    leftBottom: {x: 73.45169014635671, y: 3.853726078199864},
                    right: 134.9754744676044,
                    rightTop: {x: 134.9754744676044, y: 66.826881183998},
                    top: 66.826881183998
                },
                resolutions: [0.15211472144423888, 0.07605736072211941, 0.03802868036105977],
                scaleDenominators: [64000000, 32000000, 16000000],
            }
        }, {
            desc: "V2",
            name: "38c8028a-8f13-43a2-9119-15acdc99f34f",
            parent: "63477488-e91e-41a1-b029-c1c7b476a64d",
            timestamp: 1503472306420,
            update: {
                bounds: {
                    bottom: 3.853726078199864,
                    left: 73.45169014635671,
                    leftBottom: {x: 73.45169014635671, y: 3.853726078199864},
                    right: 134.9754744676044,
                    rightTop: {x: 134.9754744676044, y: 66.826881183998},
                    top: 66.826881183998,
                },
                resolutions: [0.15211472144423888, 0.07605736072211941, 0.03802868036105977],
                scaleDenominators: [64000000, 32000000, 16000000]
            }

        }];
    beforeAll(function () {
        testDiv = window.document.createElement("div");
        testDiv.setAttribute("id", "map");
        testDiv.style.styleFloat = "left";
        testDiv.style.marginLeft = "8px";
        testDiv.style.marginTop = "50px";
        testDiv.style.width = "500px";
        testDiv.style.height = "500px";
        window.document.body.appendChild(testDiv);

        var url = GlobeParameter.China4326URL;
        map = new ol.Map({
            target: 'map',
            controls: ol.control.defaults({attributionOptions: {collapsed: false}})
                .extend([new ol.supermap.control.Logo()]),
            view: new ol.View({
                center: [104.79, 33.03],
                zoom: 4,
                projection: 'EPSG:4326'
            })
        });

        baseLayer = new ol.source.TileSuperMapRest({
            origin: [-180, 90],
            url: url,
            wrapX: true
        });

        map.addLayer(new ol.layer.Tile({
            source: baseLayer,
            projection: 'EPSG:4326'
        }));
    });

    it("constructor", function () {
        var options = {
            layer: baseLayer,
            orientation: "horizontal"
        };
        var result = new ol.supermap.control.ChangeTileVersion(options);
        expect(result).not.toBeNull();
        expect(result.firstLoad).toBeTruthy();
        expect(result.options.collapsed).toBeTruthy();
        expect(result.options.switch).toBeTruthy();
        expect(result.options.orientation).toBe("horizontal");
        expect(result.options.title).toBe("切换缓存版本");
        expect(result.options.tooltip).toBe("top");
    });

    it("setLayer", function () {
        var options = {
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        var result = changetileversion.setLayer(baseLayer);
        expect(result).not.toBeNull();
        expect(result.firstLoad).toBeTruthy();
        expect(result.options.collapsed).toBeTruthy();
        expect(result.options.switch).toBeTruthy();
        expect(result.options.orientation).toBe("horizontal");
        expect(result.options.title).toBe("切换缓存版本");
        expect(result.options.tooltip).toBe("top");
    });

    it("removeLayer", function () {
        var options = {
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        var result = changetileversion.removeLayer();
        expect(result.options.layer).toBeNull();
    });

    it("tilesVersion", function () {
        var options = {
            layer: baseLayer,
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        var version = '63477488-e91e-41a1-b029-c1c7b476a64d';
        changetileversion.update(tileVersions);
        changetileversion.tilesVersion(version);
        expect(tileVersions[0].name).toBe(version);
    });

    it("getValue", function () {
        var options = {
            layer: baseLayer,
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        changetileversion.update(tileVersions);
        var result = changetileversion.getValue();
        expect(result).not.toBeNull();
        expect(result).toBe('0');

    });

    it("nextTilesVersion", function () {
        var options = {
            layer: baseLayer,
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        changetileversion.update(tileVersions);
        changetileversion.nextTilesVersion();
        expect(changetileversion).not.toBeNull();
        expect(changetileversion.firstLoad).toBeFalsy();
        expect(changetileversion.getValue()).toBe('0');
    });

    it("lastTilesVersion", function () {
        var options = {
            layer: baseLayer,
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        changetileversion.update(tileVersions);
        changetileversion.lastTilesVersion();
        expect(changetileversion).not.toBeNull();
        expect(changetileversion.getValue()).toBe('0');

    });

    it("setContent", function () {
        var options = {
            layer: baseLayer,
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        var version = tileVersions[0];
        changetileversion.setContent(version);
        expect(changetileversion.tooltip.innerHTML).toBe("V1");
    });

    it("getVersion", function () {
        var options = {
            layer: baseLayer,
            orientation: "horizontal"
        };
        var changetileversion = new ol.supermap.control.ChangeTileVersion(options);
        changetileversion.update(tileVersions);
        var result = changetileversion.getVersion();
        expect(result).toBe('63477488-e91e-41a1-b029-c1c7b476a64d');

    });
});

