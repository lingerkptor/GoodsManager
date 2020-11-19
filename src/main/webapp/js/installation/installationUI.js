const customized = document.getElementById('customized');

const DBName = document.getElementById('DBName');
const JDBC = document.getElementById('JDBC');
const SQLZip = document.getElementById('SQLZip');
const UploadDBFileBtn = document.getElementById('uploadDBFile');
UploadDBFileBtn.addEventListener('click', function (e) {

});
const DBList = document.getElementById('DBList');
const readDBListBtn = document.getElementById('readDBList');
readDBListBtn.addEventListener('click', function (e) {
    let option = DBList.getElementsByClassName('prototype')[0];
    installPage.getActiveDB({
        update: function (list) {
            list.forEach(db => {
                node = option.cloneNode(true);
                node.value = db;
                node.innerText = db;
                node.classList.remove('prototype');
                DBList.appendChild(node);
            });
        }
    })
});

const JDBCName = document.getElementById('JDBCName');
const URL = document.getElementById('URL');
const account = document.getElementById('account');
const password = document.getElementById('password');
const maxConn = document.getElementById('maxConn');
const installBtn = document.getElementById('install');

installBtn.addEventListener('click', function (e) {
    let installJsonObj = {
        "customized": false
    };
    if (customized.value == false)
        installPage.installDB(installJsonObj);
    else {
        installJsonObj.customized = true;

    }
});