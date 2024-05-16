
    function saveMap(){
        //MapToImg && MapToImg.excute(map);
        convert2canvas();
    }

 function convert2canvas(){
        var shareContent =document.getElementById('map');
        var width = shareContent.offsetWidth;
        var height = shareContent.offsetHeight;
        var canvas = document.createElement("canvas");
        var scale = 2;

        canvas.width = width * scale;
        canvas.height = height * scale;
        canvas.getContext("2d").scale(scale, scale);

        var opts = {
            scale: scale,
            canvas: canvas,
            logging: true,
            width: width,
            height: height,
            useCORS: true
        };
        html2canvas(shareContent, opts).then(function (canvas) {
            var img = Canvas2Image.convertToImage(canvas, canvas.width, canvas.height);
            var aa = document.createElement("a");
            aa.target = "_blank";
            aa.download="map.png";
            aa.href=img.src;
            aa.style.display="none";

            document.body.appendChild(aa);
            aa.click();
            document.body.removeChild(aa);
        });
    }

    function printMap(){
/*        $("#map").jqprint({
            debug: true,
            importCSS: true,
            printContainer: true,
            operaSupport: false
        });
        return;*/

        var shareContent =document.getElementById('map');
        var width = shareContent.offsetWidth;
        var height = shareContent.offsetHeight;
        var canvas = document.createElement("canvas");
        var scale = 2;

        canvas.width = width * scale;
        canvas.height = height * scale;
        canvas.getContext("2d").scale(scale, scale);

        var opts = {
            scale: scale,
            canvas: canvas,
            logging: true,
            width: width,
            height: height,
            useCORS: true
        };
        html2canvas(shareContent, opts).then(function (canvas) {
            var img = Canvas2Image.convertToImage(canvas, canvas.width, canvas.height);
            var div = document.createElement("div");
            div.appendChild(img);
            div.width="1000px";
            div.height="1000px";
            div.id="abc";

            //document.body.appendChild(div);
            document.getElementById('map').appendChild(div);
            $("#abc").jqprint({
                debug: true,
                importCSS: true,
                printContainer: true,
                operaSupport: false
            });
            //document.body.removeChild(div);
            document.getElementById('map').removeChild(div);
        });
    }

