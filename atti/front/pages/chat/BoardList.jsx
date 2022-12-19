import React, { useState, useEffect } from 'react'
import Link from 'next/link'
import axios from 'axios'
import curBoardAtom from '../atoms/curBoardAtom';
import { useAtom } from 'jotai';
import BoardPaging from './BoardPaging';
import authAtom from '../../public/stores/authAtom';


const BoardList = () => {
  const [boards, setBoards] = useState([]);
  const [_, SetCurBoard] = useAtom(curBoardAtom);
  const [auth, setAuth] = useAtom(authAtom);
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(1);
  const offset = (page - 1) * limit;
  let [postNum, setPostNum] = useState(1)

  const checkUser = (e) => {
    if (auth.token === null) {
      e.preventDefault();
      alert("로그인 후 작성해주세요^^");
    }
  }

  const increaseViewCount = async (postId) => {
    await axios.put(`http://localhost:3040/post/${postId}`)
      .then((res) => {
        console.log("-----------------------------------------");
        console.log(res);
        console.log("-----------------------------------------");
      })
      .catch(err => {
        console.log(postId);
      })
  }

  useEffect(() => {
    axios
      // .get('https://jsonplaceholder.typicode.com/posts')
      .get('http://localhost:3040/post/postList/main')
      .then((response) => {
        setBoards(response.data)
        console.log(response);
        console.log("보드 사이즈" + response.data.length);
      }).catch(error => {
        console.error(error)
      })

    console.log(boards);
  }, []);

  return (
    <div className="bg-[url('/images/31.jpg')] bg-cover bg-fixed opacity-80">
      <div className="hidden text-xl md:ml-10 md:block md:space-x-8 md:mr-10">

        {/* text-bold는 바깥 div, selet 둘 다에서 적용이 안 됨 */}
        <div className="pt-6 text-sm text-right">

          <div className="flex items-center flex-shrink-0">
              <Link href="/chat/BoardList" className="inline-flex items-center justify-center px-4 py-2 ml-10 mr-3 text-4xl font-extrabold text-gray-200 bg-transparent border border-transparent rounded-full shadow-sm whitespace-nowrap hover:bg-transparent hover:text-gray-600">
                잡담
              </Link>
          </div>

          <label>
            {/* 페이지 당 표시할 게시물 수:&nbsp; */}

            <select
              type="number"
              value={limit}
              onChange={({ target: { value } }) => setLimit(Number(value))}
              className="bg-purple-500 border-2 border-gray-200 rounded-lg border-opacity-20 bg-opacity-20 focus:bg-purple-200 focus:border-purple-300 focus:border-opacity-40 focus:bg-opacity-50 text-bold"
            >
              <option value="10" className="text-sm font-bold text-right text-neutral-600">10개씩</option>
              <option value="15" className="text-sm font-bold text-right text-neutral-600">15개씩</option>
              <option value="20" className="text-sm font-bold text-right text-neutral-700">20개씩</option>
              <option value="30" className="text-sm font-bold text-right text-neutral-700">30개씩</option>
              <option value="50" className="text-sm font-bold text-right text-neutral-700">50개씩</option>
            </select>
          </label>

        </div>

        <div className="mt-6 overflow-x-auto">
          <table className="w-full border-collapse table-auto">

            <thead>
              <tr className="rounded-lg text-sm font-medium text-gray-100 text-left text-[0.9674rem]">
                <th className="px-4 py-2 bg-[#6bccea99] text-center">No.</th>
                <th className="px-4 py-2 bg-[#6bccea99] text-center">Title</th>
                <th className="px-4 py-2 bg-[#6bccea99] text-left">Writer</th>
                <th className="px-4 py-2 bg-[#6bccea99] text-center">Post Date</th>
                <th className="px-4 py-2 bg-[#6bccea99] text-center">Views</th>
              </tr>
            </thead>

            {/* bg-[#c7484860] */}
            <tbody className="text-sm font-normal text-center text-gray-100">
              {boards && boards.slice(offset, offset + limit).map(board => (
                <tr key={board.postId} className="text-center py-20 border-opacity-10 border-b-2 border-gray-200 bg-[#94bee654] hover:bg-[#8ab4e97d]">

                  <td className="px-4 py-4 text-center bg-[#94bee654]">{postNum++}</td>

                  <td className="px-4 py-4 text-center">
                    <Link href="/board-category/BoardDetail" onClick={() => {
                      SetCurBoard(board.postId)
                      increaseViewCount(board.postId)
                    }} className="text-center text-gray-100 ">{board.title}
                    </Link>
                  </td>

                  <td className="px-4 py-4 text-left text-gray-100">{board.nickName}</td>

                  <td className="px-4 py-4 text-center text-gray-100">{board.createdDate}</td>

                  <td className="px-4 py-4 text-center text-gray-100">{board.viewCount}</td>

                </tr>
              ))}
            </tbody>

          </table>

          <BoardPaging
            total={boards.length}
            limit={limit}
            page={page}
            setPage={setPage}
          />

          <Link href="/board-category/WriteBoard" onClick={checkUser} className="float-right px-5 py-2 mb-10 text-base font-bold text-gray-100 border-4 rounded-lg bg-sky-300 border-violet-300 bg-opacity-40 border-opacity-40 hover:border-violet-200 hover:border-opacity-60 hover:bg-purple-300 hover:bg-opacity-60">Write</Link>
        </div>
      </div>
    </div>

  )
}
export default BoardList