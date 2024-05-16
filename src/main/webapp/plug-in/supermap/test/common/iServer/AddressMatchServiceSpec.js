require('../../../src/common/iServer/AddressMatchService');

var addressMatchURL_code = GlobeParameter.addressMatchURL_code;
var addressMatchURL_decode = GlobeParameter.addressMatchURL_decode;
describe('testAddressMatchService', function () {
    it('constructor and destroy', function () {
        var addressMatchService = new SuperMap.AddressMatchService(addressMatchURL_code);
        expect(addressMatchService).not.toBeNull();
        expect(addressMatchService.url).toEqual(addressMatchURL_code);
        expect(addressMatchService.isInTheSameDomain).toBeFalsy();
        addressMatchService.destroy();
        expect(addressMatchService.EVENT_TYPES).toBeNull();
        expect(addressMatchService.events).toBeNull();
        expect(addressMatchService.isInTheSameDomain).toBeNull();
        expect(addressMatchService.options).toBeNull();
        expect(addressMatchService.url).toBeNull();
    });
});

describe('testAddressMatchService异步', function () {
    var originalTimeout;
    beforeEach(function () {
        originalTimeout = jasmine.DEFAULT_TIMEOUT_INTERVAL;
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 50000;
    });
    afterEach(function () {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = originalTimeout;
    });
    it('code', function (done) {
        var codingFailedEventArgs = null, codingSuccessEventArgs = null;
        var options = {
            eventListeners: {"processCompleted": codeCompleted, 'processFailed': codeFailed}
        };

        function codeFailed(serviceFailedEventArgs) {
            codingFailedEventArgs = serviceFailedEventArgs;
        }

        function codeCompleted(analyseEventArgs) {
            codingSuccessEventArgs = analyseEventArgs;
        }

        var GeoCodingParams = new SuperMap.GeoCodingParameter({
            address: '公司',
            fromIndex: 0,
            toIndex: 10,
            filters: '北京市,海淀区',
            prjCoordSys: '{epsgcode:4326}',
            maxReturn: -1
        });
        var addressCodeService = new SuperMap.AddressMatchService(addressMatchURL_code, options);
        addressCodeService.code(addressMatchURL_code, GeoCodingParams);
        setTimeout(function () {
            try {
                expect(addressCodeService).not.toBeNull();
                expect(codingSuccessEventArgs).not.toBeNull();
                expect(codingSuccessEventArgs.type).toBe('processCompleted');
                expect(codingSuccessEventArgs.result).not.toBeNull();
                expect(codingSuccessEventArgs.result.length).toEqual(10);
                addressCodeService.destroy();
                GeoCodingParams.destroy();
                codingFailedEventArgs = null;
                codingSuccessEventArgs = null;
                done();
            } catch (exception) {
                console.log("'code'案例失败：" + exception.name + ":" + exception.message);
                addressCodeService.destroy();
                GeoCodingParams.destroy();
                codingFailedEventArgs = null;
                codingSuccessEventArgs = null;
                expect(false).toBeTruthy();
                done();
            }
        }, 5000)
    });

    it('decode', function (done) {
        var decodingFailedEventArgs = null, decodingSuccessEventArgs = null;
        var options = {
            eventListeners: {"processCompleted": decodeCompleted, 'processFailed': decodeFailed}
        };

        function decodeFailed(serviceFailedEventArgs) {
            decodingFailedEventArgs = serviceFailedEventArgs;
        }

        function decodeCompleted(analyseEventArgs) {
            decodingSuccessEventArgs = analyseEventArgs;
        }

        var GeoDeCodingParams = new SuperMap.GeoDecodingParameter({
            x: 116.31740122415627,
            y: 39.92311315752059,
            fromIndex: 0,
            toIndex: 5,
            filters: '北京市,海淀区',
            prjCoordSys: '{epsgcode:4326}',
            maxReturn: -1,
            geoDecodingRadius: 500
        });
        var addressDeCodeService = new SuperMap.AddressMatchService(addressMatchURL_decode, options);
        addressDeCodeService.decode(addressMatchURL_decode, GeoDeCodingParams);
        setTimeout(function () {
            try {
                expect(addressDeCodeService).not.toBeNull();
                expect(decodingSuccessEventArgs).not.toBeNull();
                expect(decodingSuccessEventArgs.type).toBe('processCompleted');
                expect(decodingSuccessEventArgs.result).not.toBeNull();
                expect(decodingSuccessEventArgs.result.length).toEqual(5);
                addressDeCodeService.destroy();
                GeoDeCodingParams.destroy();
                decodingFailedEventArgs = null;
                decodingSuccessEventArgs = null;
                done();
            } catch (exception) {
                console.log("'code'案例失败：" + exception.name + ":" + exception.message);
                addressDeCodeService.destroy();
                GeoDeCodingParams.destroy();
                decodingFailedEventArgs = null;
                decodingSuccessEventArgs = null;
                expect(false).toBeTruthy();
                done();
            }
        }, 5000)
    });

});


