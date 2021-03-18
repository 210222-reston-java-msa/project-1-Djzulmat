function login() {

    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    
    let user = {
        "username": username,
        "password": password
    }

    let xhr = new XMLHttpRequest();
    
    xhr.onload = () => {
            
        if(xhr.status === 200){

            let role = JSON.parse(xhr.getResponseHeader("user")).role;

            if(role !== 1){
                window.location = './employee-dashboard.html'; 
            }
            else{
                window.location = './manager-dashboard.html';
            }
        }
        else {
            alert('invalid credentials');
        }
    }
    xhr.open('POST', './users/login');
    
    xhr.send(JSON.stringify(user));
}