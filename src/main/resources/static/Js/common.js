document.addEventListener('DOMContentLoaded', function() {
    // 로그인 확인 및 UI 업데이트
    checkLogin();


    document.getElementById('logout').addEventListener('click', handleLogout);
    document.getElementById('mypage').addEventListener('click', goToMyPage);
    document.getElementById('Admin').addEventListener('click', () => accessToAdmin(null));
    document.getElementById('AdminAdd').addEventListener('click', () => accessToAdmin('/admin/add'));

});

async function checkLogin() {
    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');


    if (!accessToken) {
        updateLayout(false);
        adminLayout(false);
        return  { LoggedIn: false, Admin: false }; // 토큰이 없으면 바로 종료
    }

    const requestBody = { refreshToken };

    const resp = await fetch('/auth/api/checkLogin', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    });

    if (resp.ok) {
        const data = await resp.json();
        console.log('사용자가 로그인 상태입니다.', data.userId);
        localStorage.setItem('accessToken', data.accessToken);
        updateLayout(true);
        adminLayout(data.isAdmin);
        return { LoggedIn: true, Admin: data.isAdmin };
    } else {
        handleError(resp);
        return { LoggedIn: false, Admin: false };
    }
}


async function goToMyPage() {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
        alert("로그인이 필요합니다");
        return;
    }

    try {
        const resp = await fetch('/member/mypage', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        if (resp.ok) {
            document.body.innerHTML = await resp.text();
            updateLayout(true);

        } else {
            handleError(resp);
        }
    } catch (error) {
        console.error('마이페이지 호출중 예외 발생:', error);
        alert("오류 발생");
    }
}

function updateLayout(isLoggedIn) {
    document.getElementById("loggedIn").style.display = isLoggedIn ? 'block' : 'none';
    document.getElementById("loggedOut").style.display = isLoggedIn ? 'none' : 'block';
}

function adminLayout(isAdmin) {
    document.getElementById("Admin").style.display = isAdmin ? 'block' : 'none';
}



async function  accessToAdmin(goto){
    const { LoggedIn, Admin } = await checkLogin();

    if(!LoggedIn || !Admin){
        alert("권한 불충분 및 재로그인");
        return false;
    }

    let url = '/admin'

    if(goto){
        url = goto;
    }
    if(Admin){
        window.location.href = url;
    }

}

function addEventListeners() {
    // 이벤트 리스너를 재등록
    document.getElementById('AdminAdd').addEventListener('click', () => accessToAdmin('/admin/add'));
}




function handleError(response) {
    console.error('오류 발생', response.statusText);
    switch (response.status) {
        case 401:

            break;
        case 403:
            alert("접근이 거부되었습니다.");
            break;
        case 400:
            alert("세션 만료");
            window.location.href = '/';
            break;
        default:
            alert("알 수 없는 오류 발생: " + response.status);
    }
}


async function handleButtonClick(button) {
    const isLoggedIn = await checkLogin();


    const [flightId, productId] = button.id.split('-');


    if (!isLoggedIn) {
        window.location.href = '/member/login'; // 비로그인 상태이면 /login으로 리다이렉트
    } else {
        const width = 800; // 원하는 너비
        const height = 600; // 원하는 높이
        const left = (window.innerWidth / 2) - (width / 2); // 가운데 정렬
        const top = (window.innerHeight / 2) - (height / 2); // 가운데 정렬

        window.open(`/reservation/flight/detail?fid=${flightId}&pid=${productId}`, '_blank', `width=${width},height=${height},top=${top},left=${left}`);


        console.log('로그인 상태에서 버튼 클릭됨');
    }
}

async function CheckReservStat(button){
    const [aid,fid,pid] = button.id.split('-');



    const AccessToken = localStorage.getItem("accessToken");

    if(!AccessToken){
        alert("로그인후 이용해주십시오")
        window.close()
        return;
    }

    const response = await fetch(`/reservation/status?fid=${fid}&pid=${pid}&aid=${aid}`,{
        method : 'GET',
        headers : {
            'Authorization' : `Bearer ${AccessToken}`
        }
    });

    if(response.ok){

        const html = await response.text();
        document.open();
        document.write(html);
        document.close()
    } else{
        alert("문제가 발생했습니다")
    }


}


function handleLogout(event) {
    event.preventDefault(); // 기본 링크 동작 방지
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    // 로그아웃 상태로 UI 변경
    updateLayout(false);

    // 잠시 대기한 후 리다이렉트
    setTimeout(() => {
        window.location.href = '/'; // 홈 또는 로그인 페이지로 리다이렉트
        console.log('로그아웃 성공');
    }, 100); // 100ms 대기
}