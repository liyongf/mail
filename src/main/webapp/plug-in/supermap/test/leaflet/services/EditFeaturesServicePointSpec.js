require('../../../src/leaflet/services/FeatureService');

var editServiceURL = GlobeParameter.editServiceURL_leaflet;
var id1, id2, id3;

describe('leaflet_testFeatureService_editFeatures', function () {
    var originalTimeout;
    beforeEach(function () {
        originalTimeout = jasmine.DEFAULT_TIMEOUT_INTERVAL;
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 50000;
    });

    afterEach(function () {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = originalTimeout;
    });


    // 增加一个点要素，returnContent为true
    it('successEvent:addFeature_POINT', function (done) {
        var addFeatureResult_POINT = null;
        var marker = L.circleMarker([38.837029131724, 118.05408801141]);
        var addFeaturesParams = new SuperMap.EditFeaturesParameters({
            dataSourceName: "Jingjin",
            dataSetName: "Neighbor_P",
            features: marker,
            editType: "add",
            returnContent: true,
            isUseBatch: false
        });
        var addFeaturesService = L.supermap.featureService(editServiceURL).editFeatures(addFeaturesParams, function (result) {
            addFeatureResult_POINT = result
        });
        setTimeout(function () {
            try {
                expect(addFeaturesService).not.toBeNull();
                expect(addFeatureResult_POINT.type).toBe("processCompleted");
                expect(addFeatureResult_POINT.object.isInTheSameDomain).toBeFalsy();
                expect(addFeatureResult_POINT.object.options.method).toBe("POST");
                expect(addFeatureResult_POINT.object.options.data).toContain("'parts':[1]");
                expect(addFeatureResult_POINT.object.options.data).toContain('"POINT"');
                expect(addFeatureResult_POINT.result).not.toBeNull();
                expect(addFeatureResult_POINT.result.succeed).toBeTruthy();
                expect(addFeatureResult_POINT.result.length).toEqual(1);
                id1 = addFeatureResult_POINT.result[0];
                addFeaturesService.destroy();
                done();
            } catch (exception) {
                console.log("'successEvent:addFeature_POINT'案例失败：" + exception.name + ":" + exception.message);
                addFeaturesService.destroy();
                expect(false).toBeTruthy();
                done();
            }
        }, 2000)
    });

    // 批量增加点要素，isUseBatch为true
    it('successEvent:addFeatures_isUseBatch=true', function (done) {
        var addFeaturesResult = null;
        var marker1 = L.circleMarker([40, 120]);
        var marker2 = L.circleMarker([51, 100]);
        var addFeaturesParams = new SuperMap.EditFeaturesParameters({
            dataSourceName: "Jingjin",
            dataSetName: "Neighbor_P",
            features: [marker1, marker2],
            editType: "add",
            returnContent: false,
            isUseBatch: true
        });
        var addFeaturesService = L.supermap.featureService(editServiceURL).editFeatures(addFeaturesParams, function (result) {
            addFeaturesResult = result
        });
        setTimeout(function () {
            try {
                expect(addFeaturesService).not.toBeNull();
                expect(addFeaturesResult.type).toBe("processCompleted");
                expect(addFeaturesResult.object.isInTheSameDomain).toBeFalsy();
                expect(addFeaturesResult.object.isUseBatch).toBeTruthy();
                expect(addFeaturesResult.object.options.method).toBe("POST");
                expect(addFeaturesResult.object.options.data).toContain("'x':100,'y':51");
                expect(addFeaturesResult.object.options.data).toContain("'x':120,'y':40");
                expect(addFeaturesResult.result).not.toBeNull();
                expect(addFeaturesResult.result.succeed).toBeTruthy();
                expect(addFeaturesResult.result.postResultType).toBe("CreateChild");
                id2 = id1 + 1;
                id3 = id1 + 2;
                addFeaturesService.destroy();
                done();
            } catch (exception) {
                console.log("'successEvent:addFeatures_isUseBatch=true'案例失败：" + exception.name + ":" + exception.message);
                addFeaturesService.destroy();
                expect(false).toBeTruthy();
                done();
            }
        }, 2000)
    });

    // 批量删除点要素
    it('successEvent:deletePOINTs', function (done) {
        var deletePointsResult = null;
        var deleteFeaturesParams = new SuperMap.EditFeaturesParameters({
            dataSourceName: "Jingjin",
            dataSetName: "Neighbor_P",
            IDs: [id1, id2, id3],
            editType: "delete"
        });
        var deletePointsService = L.supermap.featureService(editServiceURL).editFeatures(deleteFeaturesParams, function (result) {
            deletePointsResult = result
        });
        setTimeout(function () {
            try {
                expect(deletePointsService).not.toBeNull();
                expect(deletePointsResult).not.toBeNull();
                expect(deletePointsResult.type).toBe("processCompleted");
                var id = "[" + id1 + "," + id2 + "," + id3 + "]";
                expect(deletePointsResult.object.options.data).toBe(id);
                expect(deletePointsResult.object.options.method).toBe("DELETE");
                expect(deletePointsResult.result.succeed).toBeTruthy();
                deletePointsService.destroy();
                done();
            } catch (exception) {
                expect(false).toBeTruthy();
                console.log("'successEvent:deletePoints'案例失败" + exception.name + ":" + exception.message);
                deletePointsService.destroy();
                done();
            }
        }, 2000);
    });

    // 失败事件：features为空
    it('failEvent:addFeature_featuresNull', function (done) {
        var featuresNullResult = null;
        var nullFeaturesParams = new SuperMap.EditFeaturesParameters({
            dataSourceName: "Jingjin",
            dataSetName: "Neighbor_P",
            features: [],
            editType: "add"
        });
        var nullFeaturesService = L.supermap.featureService(editServiceURL).editFeatures(nullFeaturesParams, function (result) {
            featuresNullResult = result
        });
        setTimeout(function () {
            try {
                expect(nullFeaturesService).not.toBeNull();
                expect(featuresNullResult.type).toBe("processFailed");
                expect(featuresNullResult.object.isInTheSameDomain).toBeFalsy();
                expect(featuresNullResult.object.options.method).toBe("POST");
                expect(featuresNullResult.error).not.toBeNull();
                expect(featuresNullResult.error.code).toEqual(400);
                expect(featuresNullResult.error.errorMsg).toBe("the features is empty addFeatures method");
                nullFeaturesService.destroy();
                done();
            } catch (exception) {
                console.log("'failEvent:addFeature_featuresNull'案例失败：" + exception.name + ":" + exception.message);
                nullFeaturesService.destroy();
                expect(false).toBeTruthy();
                done();
            }
        }, 2000)
    });
})