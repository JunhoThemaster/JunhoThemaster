document.addEventListener('DOMContentLoaded', function() {
    // 로그인 확인 및 UI 업데이트
    checkLogin();

    // 로그아웃 링크 클릭 이벤트 추가
    document.getElementById('logout').addEventListener('click', handleLogout);
    document.getElementById('mypage').addEventListener('click', goToMyPage);
    document.getElementById('Admin').addEventListener('click', loadAdminPage);
});

async function checkLogin() {
    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    if (!accessToken) {
        updateLayout(false);
        adminLayout(false);
        return; // 토큰이 없으면 바로 종료
    }

    const requestBody = { refreshToken };

    const response = await fetch('/auth/api/checkLogin', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    });

    if (response.ok) {
        const data = await response.json();
        console.log('사용자가 로그인 상태입니다.', data.userId);
        localStorage.setItem('accessToken', data.accessToken);
        updateLayout(true);
        adminLayout(data.isAdmin);
    } else {
        handleError(response);
    }
}


async function goToMyPage() {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
        alert("로그인이 필요합니다");
        return;
    }

    try {
        const response = await fetch('/member/mypage', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        if (response.ok) {
            document.body.innerHTML = await response.text();
            updateLayout(true);

        } else {
            handleError(response);
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

async function loadAdminPage() {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
        alert("로그인이 필요합니다");
        return;
    }

    try {
        const response = await fetch('/admin', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        if (response.ok) {
            document.body.innerHTML = await response.text();
        } else {
            handleError(response);
        }
    } catch (error) {
        console.error('호출 중 예외 발생:', error);
        alert("오류 발생");
    }
}

function handleError(response) {
    console.error('오류 발생', response.statusText);
    switch (response.status) {
        case 401:
            alert("권한이 없습니다. 다시 로그인 해주세요.");
            window.location.href = '/';
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