        var markers = [];
        var rectangles = [];
        var heatmaps = [];
        var dataPoints = [];
        var pathPoints = [];
        var lines = [];

        var graphMode = false;

        document.map = new google.maps.Map(document.getElementById("mapcanvas"));

        var latlng = new google.maps.LatLng(38.985012633461345, -77.09705424142618);
        var Options = {
            zoom: 15,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        
        var map = new google.maps.Map(document.getElementById("mapcanvas"), Options);
        map.addListener('click', function(e) {
          placeMarker(e.latLng, map);
          app.changeLatLong(e.latLng.toString());
        });

var clearPathPoints = function(){
          if(pathPoints.length!=0)
           {
            pathPoints = [];
           }
           if(lines.length!=0)
           {
            lines[0].setMap(null);
            lines[0] = null;
            lines = [];
           }            

        }
       var updateOld = function() {
          
            if (markers.length==1 && !graphMode)
              {
                markers[0].setMap(null);
                markers[0]=null;
                markers = [];
              }

            if (markers.length>=2 && graphMode)
            {
              for (i = 0; i < markers.length; i++) {
                markers[i].setMap(null);
                markers[i] = null;
              }
              clearPathPoints();
              markers = [];
            }

        }

        var placeMarker = function(latLng, map) {
          updateOld();
          console.log(markers.length)
          var marker = new google.maps.Marker({
            position: latLng,
            clickable: false,
            animation: google.maps.Animation.DROP,
            map: map
          });
          
         // console.log(markers);
         markers.push(marker);

        }
        
        var clearMap = function(){
          if(markers.length!=0)
          {
            for (i = 0; i < markers.length; i++) 
            {
              markers[i].setMap(null); 
              markers[i] = null;  
            }
            markers = [];
          }
          if(heatmaps.length!=0)
          {
            for (var i = 0; i < heatmaps.length; i++) 
            {
              heatmaps[i].setMap(null);   
            }
             heatmaps = [];
          }
          if(rectangles.length!=0)
          {
            for (var i = 0; i < rectangles.length; i++) 
            {
              rectangles[i].setMap(null);   
            }
             rectangles = [];
          }
          clearPathPoints();
          pathPoints = [];
          
            
        }

        var toggleGraphMode = function(){
          graphMode = (!graphMode);
          clearMap();
        }
  
        
        var addPathPoints = function(x, y){
          pathPoints.push({lat: x, lng: y});
        }
            

     
        var showLine = function(){
          if(pathPoints.length!=0)
          {
            var dLine = new google.maps.Polyline({
            path: pathPoints,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });
          
          dLine.setMap(map);
          lines.push(dLine);
          }
          pathPoints = [];
        }


        var addDataPoint = function(x, y){
            dataPoints.push(new google.maps.LatLng(x, y));
        }

        var clearDataPoints = function(){
          if(dataPoints.length!=0)
           {
            dataPoints = [];
           }
           if(heatmaps.length!=0)
           {
            heatmaps[0].setMap(null);
            heatmaps = [];
           }            

        }
        

        var showBox = function(x, y, x2, y2){
          if(markers.length!=0)
          {
            map.panTo(markers[0].getPosition());
            
            if(rectangles.length==1)
            rectangles[0].setMap(null);

          var rectangle = new google.maps.Rectangle({
          strokeColor: '#FF0000',
          strokeOpacity: 0.8,
          strokeWeight: 2,
          clickable: false,
          fillColor: '#FF0000',
          fillOpacity: 0.2,
          map: map,
          bounds: new google.maps.LatLngBounds(new google.maps.LatLng(x,y), 
                  new google.maps.LatLng(x2, y2)),
          });
          rectangles[0] = rectangle;
          map.fitBounds(rectangle.getBounds());
          
        }
      }

        var showHeatMap = function(){
          if(rectangles.length==1)
            rectangles[0].setMap(null);
          heatmap = new google.maps.visualization.HeatmapLayer({
                    data: dataPoints,
                    map: map
            });
          heatmaps.push(heatmap);
        }   

       var toggleHeatmap = function() {
        heatmap.setMap(heatmap.getMap() ? null : map);
      }

        var clearAllHeatMaps = function() {
          if(heatmaps.length!=0)
          {
            for (var i = 0; i < heatmaps.length; i++) {
              heatmaps[i].setMap(null);
            }
          }
        }
        

    