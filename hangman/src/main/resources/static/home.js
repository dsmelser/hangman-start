function startGame(){

    $.ajax( {
        url: 'http://localhost:8080/api/begin',
        method: 'POST',
        success: function( id ){

            loadGame( id );


        }
    } );

}

function loadGame( id ){

    $.ajax( {
        url: 'http://localhost:8080/api/gamestate/' + id,
        method: 'GET',
        success: function( data ){
            loadBoard( data );

        }
    });

}

function loadBoard( board ){
    //raw JS
    // let uncoveredParagraph = document.getElementById( 'uncovered' );
    // uncoveredParagraph.innerHTML = data.partial;


    // jQuery
    // takes in a CSS selector
    $( '#uncovered' ).text( board.partial );

    let letterString = '';
    for( let i = 0; i < board.guessedLetters.length; i++ ){
        letterString += board.guessedLetters[i]  + ' '
    }
    $( '#selectedLetters' ).text( letterString );
    $( '#gameId' ).val( board.gameId );
    $( '#movesRemaining').text( 'Remaining Moves: ' + board.movesRemaining );
}

function submitGuess(){

    let guessObject = {};
    guessObject.gameId = $('#gameId').val();
    guessObject.guess = $('#chosenLetter').val();

    $.ajax( {
        method: 'PUT',
        url: 'http://localhost:8080/api/guessletter',
        contentType: "application/json",
        data: JSON.stringify(guessObject),
        success: function ( board ){
            loadBoard( board );
        },
        error: function( jqXHR, statusText, errorThrown ){
            alert( "could not call service");
        }
    });

    alert('error');
}