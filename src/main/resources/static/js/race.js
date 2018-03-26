var EsBuilder = function (streamUrl) {

    var es;
    var eventListeners = {};

    return {
        addEventListener: function (eventName, event) {
            eventListeners[eventName] = event;
            return this;
        },
        build: function () {
            es = new EventSource(streamUrl);

            $.each(eventListeners, function (k, v) {
                es.addEventListener(k, v, false);
            });
            return es;
        }
    }
};

function enableReplayButton() {
    $('#replayBtn').prop("disabled", false);
}

function disableReplayButton() {
    $('#replayBtn').prop("disabled", true);
}

function setBtnText(text) {
    $('#btn').text(text);
}

function drawCars(cars) {
    var $joinListArea = $('#joinList');
    $joinListArea.empty();
    cars.forEach(function(e) {
        $joinListArea.append('<div>'+e["name"]+'</div>');
    });

}

function drawRace(raceList) {
    var $raceListArea = $('#raceList');
    $raceListArea.empty();
    raceList.forEach(function(e) {
        $raceListArea.append('<div>'+e["car"]["name"]+'</div><div style="margin-left: '+ ( e["distance"] * 10) +'px;">&#128652;</div>');
    });
}

function announcement(text) {
    $('#announcementArea').text(text);
}