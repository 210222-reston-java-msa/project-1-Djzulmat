
function getReimbursements() {
    let xhr = new XMLHttpRequest(); 
    let txt = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let reimbursement = JSON.parse(xhr.responseText);
            for (x in reimbursement) {
                let status = '';
                let d = new Date(parseInt(reimbursement[x].submitted));
                reimbursement[x].submitted = d.toLocaleDateString();
                if(reimbursement[x].resolved != null){
                    let rd = new Date(parseInt(reimbursement[x].resolved));
                    reimbursement[x].resolved = rd.toLocaleDateString();
                }
                else {
                    reimbursement[x].resolved = 'Unresolved';
                    reimbursement[x].resolverId = 'N/A';
                }

                if(reimbursement[x].status === 1){
                    reimbursement[x].status = 'Approved';
                    status = 'approved';
                }
                else if(reimbursement[x].status === 2){
                    reimbursement[x].status = 'Denied';
                    status = 'denied';
                }
                else{
                    reimbursement[x].status = 'Pending';
                    status = 'pending';
                }

                if(reimbursement[x].type === 1){
                    reimbursement[x].type = 'Lodging';
                }
                else if(reimbursement[x].type === 2){
                    reimbursement[x].type = 'Travel';
                }
                 else if(reimbursement[x].type === 3){
                    reimbursement[x].type = 'Food';
                }
                else{
                    reimbursement[x].type = 'Other';
                }

                txt += `<tr class=${status}>`;
                txt += `<td>${reimbursement[x].id}</td>`;
                txt += `<td>$${reimbursement[x].amount}</td>`;
                txt += `<td>${reimbursement[x].submitted}</td>`;
                txt += `<td>${reimbursement[x].resolved}</td>`;
                txt += `<td>${reimbursement[x].description}</td>`;
                txt += `<td>${reimbursement[x].authorId}</td>`;
                txt += `<td>${reimbursement[x].resolverId}</td>`;
                txt += `<td>${reimbursement[x].status}</td>`;
                txt += `<td>${reimbursement[x].type}</td>`;
                txt += `</tr>`; 
             
                document.getElementById("table").innerHTML = txt;
            }
        }
    }
    xhr.open('GET', './reimbursements/all');
    xhr.send();
}

function submitReimbursement() {
    let type = document.getElementById('type').value;
    let typeId;
    if(type === 'Lodging'){
        typeId = 1;
    }
    else if(type === 'Travel'){
        typeId = 2;
    }
    else if(type === 'Food'){
        typeId = 3;
    }
    else{
        typeId = 4;
    }
    
    let status = 0;
    let resolver = null;
    let amount = document.getElementById('amount').value;
    let description = document.getElementById('description').value;
    let submitted = Date.now();
    let author;
    
    let newReimbursement = {
        "amount": amount,
        "submitted": submitted,
        "resolved": null,
        "description": description,
        "authorId": author,
        "resolverId": resolver,
        "status": status,
        "type": typeId,
    }
    
    let xhr = new XMLHttpRequest();
    xhr.onload = () => {
        if(xhr.status === 200){
            console.log("showing new view");
            alert('Added new reimbursement');
            window.location = './manager-dashboard.html';
        }
        else {
            alert('Unable to add the reimbursement');
        }
    }
    
    xhr.open('POST', './reimbursements/add');
    xhr.send(JSON.stringify(newReimbursement)); 
}

function approveOrDeny() {
    let choice = document.getElementById('status').value;
    let reimbId = document.getElementById('reimbId').value;
    
    let statusId;
    if(choice === 'Approve'){
        statusId = 1;
    }
    else if (choice === 'Deny') {
        statusId = 2;
    }
    
    let reimbursement = {
        "id": parseInt(reimbId),
        "status": statusId
    }
    let xhr = new XMLHttpRequest();
    xhr.onload = () => {
        if(xhr.status === 200) {
        	
        	getReimbursements();
           // window.location = './manager-dashboard.html';
           $("#statuses").modal('hide');

            
        }
        else {
            alert('Unable to change the status. Please try again later.');
        }
    }
    if(statusId === 1){
        xhr.open('POST', './reimbursements/approve');
        xhr.send(JSON.stringify(reimbursement));
    }
    else {
        xhr.open('POST', './reimbursements/deny');
        xhr.send(JSON.stringify(reimbursement));
    }
}


function logout() {
    let xhr = new XMLHttpRequest();
    
    xhr.onload = () => {
        if(xhr.status === 200){
            window.location = './login.html';
        }
        else {
            alert('failed to logout');
        }
    }
    xhr.open('GET', './users/logout');
    xhr.send();
}



{/* <tr data-id=`${resimnesents[x].id}`>
  </tr>

}

<tr data-id= data-amount='23' >
  <td>1</td>
  
</tr>

$("tr").on('click', function() {
  let element = this

  let attributes = element.dataset

  $('#new').modal('show')

  $('#amount').html(attributes.amount)

  console.log(element.dataset)
  
}) */}
