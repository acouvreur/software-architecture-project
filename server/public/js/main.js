var xhr = new XMLHttpRequest();
var address = "blablamove/chaosbrocker"
xhr.open('PATCH', address);
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.onload = function() {
    if (xhr.status === 200) {
        var userInfo = JSON.parse(xhr.responseText);
    }
};
/*
xhr.send(JSON.stringify({
    name: 'John Smith',
    age: 34
}));
*/

function chaosAccounting() {
    console.log("Chaos Accounting");
    var slowdown = document.getElementById("slowdown-accounting").value;
    var deleteP = document.getElementById("delete-accounting").value;
    var duplicate = document.getElementById("duplicate-accounting").value;
    var salt = document.getElementById("salt-accounting").value;
    var nothing = document.getElementById("nothing-accounting").value;
    //TODO : Send to chaos brocker of accounting service
}
function chaosAnnouncement() {
    console.log("Chaos Announcement")
    var slowdown = document.getElementById("slowdown-announcement").value;
    var deleteP = document.getElementById("delete-announcement").value;
    var duplicate = document.getElementById("duplicate-announcement").value;
    var salt = document.getElementById("salt-announcement").value;
    var nothing = document.getElementById("nothing-announcement").value;
    //TODO : Send to chaos brocker of announcement service
}

function chaosTracking() {
    console.log("Chaos Tracking")
    var slowdown = document.getElementById("slowdown-tracking").value;
    var deleteP = document.getElementById("delete-tracking").value;
    var duplicate = document.getElementById("duplicate-tracking").value;
    var salt = document.getElementById("salt-tracking").value;
    var nothing = document.getElementById("nothing-tracking").value;
    //TODO : Send to chaos brocker of tracking service
}
  
function chaosMatching() {
    console.log("Chaos Matching")
    var slowdown = document.getElementById("slowdown-matching").value;
    var deleteP = document.getElementById("delete-matching").value;
    var duplicate = document.getElementById("duplicate-matching").value;
    var salt = document.getElementById("salt-matching").value;
    var nothing = document.getElementById("nothing-matching").value;
    //TODO : Send to chaos brocker of matching service
}
  
function chaosBilling() {
    console.log("Chaos Billing")
    var slowdown = document.getElementById("slowdown-billing").value;
    var deleteP = document.getElementById("delete-billing").value;
    var duplicate = document.getElementById("duplicate-billing").value;
    var salt = document.getElementById("salt-billing").value;
    var nothing = document.getElementById("nothing-billing").value;
    //TODO : Send to chaos brocker of billing service
}

(function ($) {
    "use strict";


    /*==================================================================
    [ Focus Contact2 ]*/
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    })
  
  
    /*==================================================================
    [ Validate ]*/
    var name = $('.validate-input input[name="name"]');
    var message = $('.validate-input textarea[name="message"]');


    $('.validate-form').on('submit',function(){
        var check = true;

        if($(name).val().trim() == ''){
            showValidate(name);
            check=false;
        }

        if($(message).val().trim() == ''){
            showValidate(message);
            check=false;
        }

        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
       });
    });

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    

})(jQuery);