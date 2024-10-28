window.onload = function() {
    // 세션에서 액세스 토큰과 사용자 ID를 가져오는 API 호출
    fetch('/api/session-info') // 세션 정보 API 엔드포인트
        .then(response => {
            if (!response.ok) {
                throw new Error('로그인에 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            const accessToken = data.accessToken;
            const userId = data.userId;

            if (accessToken) {
                // 액세스 토큰을 로컬 스토리지에 저장
                localStorage.setItem('accessToken', accessToken);

                window.location.href = "/"; // 홈 페이지로 이동
            } else {

            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message);
        });
};