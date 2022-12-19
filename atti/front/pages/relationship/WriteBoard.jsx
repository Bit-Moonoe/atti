import React, { useEffect, useState } from 'react'
import Link from 'next/link'
import curBoardAtom from '../atoms/curBoardAtom';
import { useAtom } from 'jotai';
import axios from 'axios';
import { call } from '../../service/ApiService';
import authAtom from '../../public/stores/authAtom';
import { useRouter } from 'next/router'


const WriteBoard = () => {
  const [postId, setPostId] = useState("")
  // const [category, setCategory] = useState("")
  const [title, setTitle] = useState("")
  const [content, setContent] = useState("")
  const [curBoard, setCurBoard] = useAtom(curBoardAtom);
  const [auth, setAuth] = useAtom(authAtom);
  const router = useRouter()

  const writeTitle = (event) => {
    setTitle(event.target.value)
  }

  const writeContent = (event) => {
    setContent(event.target.value)
  }

  const req = {
    // category: category,
    title: title,
    content: content
  }

  const handleCreate = () => {
    call("/post/writePost", "POST", req)
      .then((res) => {
        console.log(res);

        router.push("/Board")
      }) 
      .catch((err) =>{
        console.log(err);
      })
  }


  return (
    <>
    <form>
    <div className="bg-[url('/images/10.jpg')] bg-cover opacity-80">
        <div className='max-w-2xl px-6 py-10 m-auto bg-gray-200 rounded-md bg-opacity-20'>
          <div className="pl-10 mb-6 text-lg font-bold text-left text-teal-500 border-none">잡담</div>
          <div>
            <div className="pl-10 mb-10 text-2xl font-bold text-left text-gray-700 border-b-2">
              Title
              <input type="text" name="title" value={title} placeholder="고민을 한 마디로 알려주세요" onChange={writeTitle} className="w-full py-4 pl-5 text-sm text-left text-gray-800 placeholder-gray-300 border-none hover:placeholder-transparent" />
            </div>
          </div>
          <div>
            <div className="items-center w-full h-[400px] text-gray-700 bg-gray-100 rounded-md resize-none mb-9 text-center">
              
              <textarea type="text" name="content" value={content} placeholder="당신의 고민을 적어보세요" onChange={writeContent} className="items-center w-full h-[400px] text-gray-800 placeholder-gray-300 hover:placeholder-transparent bg-gray-100 bg-opacity-30 rounded-md resize-none py-10 px-10 mb-9 text-left" />
              </div>
          </div>
          <Link href='/board-category/BoardList'>
            <button type='create' onClick={handleCreate} className='float-right px-5 py-2 font-bold text-blue-500 border-2 rounded-lg border-sky-500 hover:bg-sky-300'>Upload</button>
          </Link>
        </div>
        </div>
      </form>
    </>
  )
}
export default WriteBoard