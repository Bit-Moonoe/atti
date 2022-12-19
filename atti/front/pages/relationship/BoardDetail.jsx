import { useAtom } from 'jotai'
import React, { useEffect, useState } from 'react'
import Link from 'next/link'
import curBoardAtom from '../atoms/curBoardAtom'
import { call } from '../../service/ApiService';

const BoardDetail = () => {

    // const [category, setCategory] = useState("")
    const [postId, setPostId] = useState("1")
    const [title, setTitle] = useState("")
    const [nickName, setNickName] = useState("")
    const [createdDate, setCreatedDate] = useState("")
    const [viewCount, setViewCount] = useState("")
    const [content, setContent] = useState("")
    const [curBoard, setCurBoard] = useAtom(curBoardAtom);

    // const [commentId, setCommentId] = useState("") // 댓글 고유 번호
    // const [postSeq, setPostSeq] = useState("") // 게시글 번호
    // const [commentContent, setCommentContent] = useState("")

    const handleDetail = () => {

        // req data 차이점??
        const req = {
            // category,
            postId : curBoard,
            title : title,
            nickName : nickName,
            createdDate : createdDate,
            viewCount : viewCount,
            content :  content,
            // comment
        }

        console.log(curBoard);
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(req)
        };

        fetch('http://localhost:3040/post/selectPost', options)
            .then(response => response.json())
            // .then(data => console.log(data))
            .catch(error => console.error('실패', error));
        console.log("handleDetail clicked button")
    }


    useEffect(() => {
        fetch(`http://localhost:3040/post/selectPost/${curBoard}`)
            .then(response => response.json())
            .then(response => {console.log(response)
                // setCategory(response.category)
                setPostId(response.postId);
                setTitle(response.title)
                setNickName(response.nickName)
                setCreatedDate(response.createdDate)
                setViewCount(response.viewCount)
                setContent(response.content)
                // setComment(response.comment)
            })
            .catch(error => console.error(error))
    }, [])
  

    return (
    
        <div className="bg-[url('/images/31.jpg')] bg-cover bg-fixed opacity-80">
            <form>
                <div className='max-w-2xl px-6 py-10 m-auto bg-pink-500 rounded-md bg-opacity-30'>
                    {/* <div className='mx-auto my-20 border-2 border-separate rounded-lg shadow-2xl border-spacing-6 border-neudival-300'> */}

                    <div className="mb-6 text-xl font-bold text-left text-gray-100 border-none">
                        <Link href="/relationship/BoardList" className='text-gray-100'>
                            대인관계 &raquo;
                        </Link>
                    </div>

                    {/* <div className="px-6 py-2 text-xs text-gray-900">
                                        <input type="text" readOnly value={curBoard} onChange={(event) => setPostId(event.target.value)} className="px-6 py-4 text-sm font-medium text-left text-gray-900 border-2" />
                        </div> */}

                    {/* 제목 및 작성 관련 */}
                    <div className="max-w-2xl px-6 pt-10 pb-5 m-auto bg-transparent border-none rounded-md">
                        <input type="text" readOnly value={title} placeholder="고민을 한 마디로 알려주세요" onChange={(event) => setTitle(event.target.value)} className="py-4 pr-6 text-4xl font-bold text-left text-gray-100 bg-transparent border-none" />        
                    </div>

                    <div className="max-w-2xl px-6 py-1 m-auto bg-transparent rounded-md">
                        <input type="text" readOnly value={nickName} onChange={(event) => setNickName(event.target.value)} className="py-4 pr-6 text-sm font-bold text-left text-gray-300 bg-transparent border-none" />        
                    </div>

                    <div className="max-w-2xl px-6 py-1 m-auto bg-transparent rounded-md">
                        
                        <input type="text" readOnly value={createdDate} onChange={(event) => setCreatedDate(event.target.value)} className="px-6 py-4 text-xs font-medium text-left text-gray-300 bg-transparent border-none" />
                       
                        <input type="text" readOnly value={viewCount} onChange={(event) => setViewCount(event.target.value)} className="px-6 py-4 text-xs font-medium text-left text-gray-300 bg-transparent border-none" />
                    </div>

                    {/* 본문 창 */}
                    <div>
                        <div className="px-6 py-4 text-sm font-medium text-left text-gray-900 border-2">
                            <input readOnly value={content} placeholder="당신의 고민을 적어보세요" onChange={event => setContent(event.target.value)} className="px-6 py-4 text-sm font-medium text-left text-gray-200 bg-transparent border-none" />
                        </div>
                    </div>

                    {/* 댓글창 */}
                    {/* <div className="mb-6">
                        <input name="message" placeholder="댓글입력" onChange={writeContent} className="resize-none focus:outline-none  w-full rounded-lg p-2 text-[20px] bg-gray-100 border border-transparent appearance-none rounded-tg placeholder-gray-400" />
                        <div className="flex justify-between mt-2">
                            <Link href='/board-category/BoardDetail'>
                                <button type="submit" onClick={handleCreate} className="flex items-center float-right px-4 py-2 text-sm text-white bg-blue-600 rounded-md shadow-lg">Send
                                    <svg className="ml-1" viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" strokeWidth="2" fill="none" strokeLinecap="round" strokeLinejoin="round"><line x1="22" y1="2" x2="11" y2="13"></line><polygon points="22 2 15 22 11 13 2 9 22 2"></polygon></svg>
                                </button>
                            </Link>
                        </div>
                    </div> */}

                    <br></br>
                    <br></br>
                    <br></br>
                    <br></br>
                    <br></br>
                    <br></br>
                    <br></br>
                    <Link href='/board-category/ModifyBoard'>
                        <button type='modify' onClick={handleDetail} className='px-5 py-2 mx-3 font-bold text-gray-100 border-2 rounded-lg hover:bg-neudival-200 '>Modify</button>
                    </Link>
                </div>
            </form>
            </div>
        

    )
}

export default BoardDetail