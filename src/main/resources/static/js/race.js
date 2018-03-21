var stream_url = "/racing/game/react";

if (!!window.EventSource) {
    var es = new EventSource(stream_url);
    es.addEventListener('message', function(e) {
        console.log(event);

        var game = JSON.parse(event.data);
        console.log(game);

        switch (game["gameStatus"]) {
            case "READY":
                $('#btn').text("start!!!");
                break;
            case "RACING":
                $('#btn').text("now playing! please wait! remain "+game["remainTurns"]+" turns.");
                break;
            default:
                $('#btn').text("default");
        }

        var $joinListArea = $('#joinList');
        var $raceListArea = $('#raceList');

        $joinListArea.empty();
        $raceListArea.empty();

        game["raceList"].forEach(function(e) {
            $('#raceList').append('<div>'+e["car"]["name"]+'</div><div style="margin-left: '+ ( e["distance"] * 10) +'px;">&#128652;</div>');
        });

        game["cars"].forEach(function(e) {
            $('#joinList').append('<div>'+e["name"]+'</div>');
        });

        $('#announcementArea').text(game["announcement"]);


    }, false);

    es.addEventListener('open', function(e) {
        // Connection was opened.
        console.log("open");
    }, false);

    es.addEventListener('error', function(e) {
        if (e.readyState == EventSource.CLOSED) {
            // Connection was closed.
            console.error(e);
        }
    }, false);

} else {
    // Result to xhr polling :(
}
