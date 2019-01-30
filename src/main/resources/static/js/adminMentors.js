var email = document.getElementById("email");
var button = document.getElementById("button");


function addMentor(){
var id = document.getElementById("id");
var name = document.getElementById("name");
var surname = document.getElementById("surname");
alert("You added: \n id: " + id.value+" name : "+name.value+" sur: "+surname.value);
}
function removeMentor(){
var id = document.getElementById("idToRemove");
alert("Mentor deleted " + id.value) 
}



