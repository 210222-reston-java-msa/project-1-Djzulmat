function logout() {
    let xhr = new XMLHttpRequest();
    
    xhr.onload = () => {
        if(xhr.status === 200){
            window.location = './login-page.html';
        }
        else {
            alert('failed to logout');
        }
    }
    xhr.open('GET', './users/logout');
    xhr.send();
}