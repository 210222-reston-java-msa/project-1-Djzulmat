function register() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let firstname = document.getElementById("firstname").value;
    let lastname = document.getElementById("lastname").value;
    let email = document.getElementById("email").value;
    let role = document.getElementById("role").value;
    let roleId;
    
    if (role === "Employee") {
	
        roleId = 2;

    }  else {
	
        roleId = 1;

    }
    
    let newUser = {
        "username": username,
        "password": password,
        "firstName": firstname,
        "lastName": lastname,
        "email": email,
        "role": roleId
    }
    
    let xhr = new XMLHttpRequest();

    xhr.onload = () => {
	
        if (xhr.status === 200) {
	
            alert('Added user to the system');
            window.location = './manager-dashboard.html';

        } else {
	
            alert('Please try again');

        }

    }

    xhr.open('POST', './users/register');
    xhr.send(JSON.stringify(newUser));

}