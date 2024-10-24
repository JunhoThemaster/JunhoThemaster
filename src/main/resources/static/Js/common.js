document.addEventListener('DOMContentLoaded', function() {
    // 로그인 확인 및 UI 업데이트
    checkLogin();

    // 로그아웃 링크 클릭 이벤트 추가
    document.getElementById('logout').addEventListener('click', function(event) {
        event.preventDefault(); // 기본 링크 동작 방지

        // 로컬 스토리지에서 토큰 삭제
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');

        // UI 업데이트
        updateLayout(false); // 로그아웃 상태로 UI 변경
        window.location.href = '/'; // 홈 또는 로그인 페이지로 리다이렉트
        console.log('로그아웃 성공');
    });

    document.getElementById('mypage').addEventListener('click', async function (event){
        event.preventDefault()
        await goToMyPage();
    })
});
//매 페이지 마다 호출 될 예정
async function checkLogin() {
    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    const requestBody = {
        refreshToken: refreshToken
    };

    const response = await fetch('/auth/api/checkLogin', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken || ''}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    });

    // 로그인 상태에 관계없이 페이지 이동 로직
    if (response.ok) {
        const data = await response.json();
        if (data.accessToken) {
            console.log('사용자가 로그인 상태입니다.',data.userId);
            updateLayout(true);
            localStorage.setItem('accessToken', data.accessToken); // 새로운 액세스 토큰 저장

        } else {
            console.log('사용자가 로그인하지 않았습니다.');
            updateLayout(false);
        }
    } else {
        updateLayout(false);
        console.error('로그인 확인 중 오류 발생:', response.statusText);
        // 로그인 페이지로 리다이렉션하는 로직
    }
}

// 마이페이지 호출시
async function goToMyPage(){
    const accessToken =     localStorage.getItem('accessToken');

    if(!accessToken){
        alert("로그인이 필요합니다")
        return;
    }

    try{
        const response = await fetch('/member/mypage',{
            method : 'GET',
            headers : {
                'Authorization' : `Bearer ${accessToken}`
            }
        });
        if(response.ok){
            const data = await response.text();
            document.body.innerHTML = data;

        }else{
            console.error('마이페이지 불러오기 오류',response.statusText);
            if(response.status === 400){
                alert("세션 만료");
                window.location.href = '/';
            }
        }
    }catch (error){
        console.error('마이페이지 호출중 예외 발생: ' , error)
        alert("오류 발생")
    }
}

function updateLayout(isLoggedIn) {
    if (isLoggedIn) {
        // 로그인 상태일 때의 UI 변경
        document.getElementById("loggedIn").style.display = 'block';
        document.getElementById("loggedOut").style.display = 'none';
    } else {
        // 로그인하지 않았거나 리프레시 토큰이 만료된 상태일 때의 UI 변경
        document.getElementById("loggedIn").style.display = 'none';
        document.getElementById("loggedOut").style.display = 'block';
        // 추가적인 UI 변경이 필요하다면 여기에 추가
    }
}