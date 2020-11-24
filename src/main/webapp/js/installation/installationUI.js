const UIConfiger = function () {
    const customized = document.getElementById('customized');

    const DBName = document.getElementById('DBName');
    const JDBC = document.getElementById('JDBC');
    const SQLZip = document.getElementById('SQLZIP');
    const uploadResult = document.getElementById('uploadResult');
    const UploadDBFileBtn = document.getElementById('uploadDBFile');
    UploadDBFileBtn.addEventListener('click', function (e) {
        formObj = new FormData();
        formObj.append('DBName', DBName.value);
        formObj.append('JDBC', JDBC.files[0]);
        formObj.append('SQLZIP', SQLZip.files[0]);
        installPage.uploadDBFile(formObj, {
            update: function (success) {
                if (success)
                    uploadResult.innerText = '上傳成功';
                else uploadResult.innerText = '上傳失敗';
            }
        });
    });
    const DBList = document.getElementById('DBList');
    const readDBListBtn = document.getElementById('readDBList');
    readDBListBtn.addEventListener('click', function (e) {
        let option = DBList.getElementsByClassName('prototype')[0].cloneNode(true);
        installPage.getActiveDB({
            update: function (list) {
                DBList.innerHTML = '';
                DBList.appendChild(option);
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
    const installResult = document.getElementById('installResult');
    installBtn.addEventListener('click', function (e) {
        let installJsonObj = {
            "customized": false
        };
        if (customized.checked) {
            installJsonObj.customized = true;
            installJsonObj.databaseName = DBList.options[DBList.selectedIndex].value;
            installJsonObj.JDBCName = JDBCName.value;
            installJsonObj.URL = URL.value;
            installJsonObj.account = account.value;
            installJsonObj.password = password.value;
            installJsonObj.maxConnection = maxConn.value;
        }
        installPage.installDB(installJsonObj, {
            update: function (resultObj) {
                if (resultObj.buildDBConfig)
                    installResult.innerHTML = "<p>建立資料庫成功</p>";
                else
                    installResult.innerHTML = "<p>建立資料庫失敗</p>";
                if (resultObj.testConnect)
                    installResult.innerHTML += "<p>連接資料庫成功</p>";
                else
                    installResult.innerHTML += "<p>連接資料庫失敗</p>";
                if (resultObj.createTable)
                    installResult.innerHTML += "<p>新增資料表成功</p>";
                else
                    installResult.innerHTML += "<p>新增資料表失敗</p>";
            }
        });

    });
};