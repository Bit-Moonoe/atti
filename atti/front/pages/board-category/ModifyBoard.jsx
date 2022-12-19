import { useAtom } from 'jotai'
import React, { useEffect, useState } from 'react'
import Link from 'next/link'
import curBoardAtom from '../atoms/curBoardAtom'
import Router, { useRouter } from 'next/router'

const ModifyBoard = () => {

    const [postId, setPostId] = useState("")
    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")
    const [curBoard, setCurBoard] = useAtom(curBoardAtom);
    const router = useRouter();

    const handleModify = () => {

        if(title === "" || content === ""){
            alert("제목 및 내용을 입력해주세요.")
             
        }else{

        let headers = new Headers({
            "Content-Type": "application/json",
        })

        const accessToken = sessionStorage.getItem("ACCESS_TOKEN")
        if(accessToken && accessToken !== null){
            headers.append("Authorization", "Bearer " + accessToken);
        }

        const req = {
            postId : curBoard,
            title: title,
            content: content,
        }
        console.log(req);
        console.log("글 수정을 위한 엑세스 토큰 확인: " +accessToken );
        const options = {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify(req)
        };

        fetch('http://localhost:3040/post', options)
            .then(response => {response.json();
                if (response.ok) { alert("수정이 완료되었습니다.") 
                router.push("/Board")
            } else { alert("글 작성자만 수정 가능합니다.")
        }})
            .catch(error => console.error('글 작성자만 수정 가능합니다.', error));
        console.log("handleModify clicked button")
        }
    }

    const handleDelete = () => {

        let headers = new Headers({
            "Content-Type": "application/json",
        })

        const accessToken = sessionStorage.getItem("ACCESS_TOKEN")
        if(accessToken && accessToken !== null){
            headers.append("Authorization", "Bearer " + accessToken);
        }

        const req = {
            postId: curBoard,
            title,
            content
        }
        console.log(req);
        console.log(curBoard);
        const options = {
            method: 'DELETE',
            headers: headers,
            body: JSON.stringify(req)
        };

        fetch(`http://localhost:3040/post`, options)
            .then(response => {
                if (response.ok) { alert("삭제가 완료되었습니다.") 
                router.push("/Board")
            } else { alert("글 작성자만 삭제 가능합니다.") }
            })
            .catch(response => response.resMessage);
        console.log("handledelete clicked button")
    }

    useEffect(() => {
        fetch(`http://localhost:3040/post/selectPost/${curBoard}`)
            .then(response => response.json())
            .then(data => {
                console.log("현재 게시글 번호:" + curBoard);
                setPostId(data.postId);
                setTitle(data.title);
                setContent(data.content);
            })
            .catch(error => console.error(error))
    }, [])


    return (
        
        <div className="bg-[url('/images/31.jpg')] bg-cover bg-fixed opacity-80">
                <div className='max-w-2xl px-6 py-10 m-auto bg-gray-300 rounded-md bg-opacity-20'>
                 
                    <div className="mb-6 text-2xl font-bold text-left text-gray-200 border-4">잡담</div>
                    <div>
                        <div className="mb-10 text-2xl font-bold text-center text-gray-200 border-4">
                            번호<input type="text" readOnly value={curBoard} className="w-full py-4 text-sm text-center text-gray-900 border-2 px-30" />
                        </div>
                    </div>
                    <div>
                        <div className="mb-10 text-2xl font-bold text-center text-gray-200 border-4">
                            제목
                            <input type="text" name="title" value={title} placeholder="고민을 한 마디로 알려주세요" onChange={(event) => setTitle(event.target.value)} className="w-full py-4 text-sm text-left text-gray-900 border-2 px-30" />
                        </div>
                    </div>
                    <div>
                        <div className="items-center w-full h-[400px] text-gray-700 bg-gray-100 rounded-md resize-none mb-9 text-center">
                            내용
                            <textarea type="text" name="content" value={content} placeholder="당신의 고민을 적어보세요" onChange={event => setContent(event.target.value)} className="items-center w-full h-[400px] text-gray-700 bg-gray-100 rounded-md resize-none mb-9 text-center" />
                        </div>
                    </div>
                        <button type='delete' onClick={handleDelete } className='float-right px-5 py-2 font-bold border-2 rounded-lg text-neutral-100 hover:bg-sky-300'>글 삭제</button>
                        <button type='modify' onClick={handleModify} className='float-right px-5 py-2 font-bold border-2 rounded-lg text-neutral-100 hover:bg-sky-300'>글 수정</button>

                </div>
        </div>
    )
}

export default ModifyBoard