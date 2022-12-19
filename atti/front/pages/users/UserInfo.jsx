import { useAtom } from 'jotai'
import React from 'react'
import authAtom from '../../public/stores/authAtom'



const UserInfo = () => {

  const [auth, setAuth] = useAtom(authAtom)
//   console.log(auth.token);
  console.log(auth.nickName);
  console.log(auth.joinDate);

  return (
    <>
    <div className="bg-[url('/images/25.jpeg')] bg-cover bg-fixed opacity-80">
            <div className='container flex justify-center mx-auto'>
                <div className="flex flex-col">
                    <div className="w-full">
                        <div className="overflow-hidden">
                            <table className='mx-auto my-20 border-2 border-separate rounded-lg shadow-2xl border-spacing-6 border-neutral-300'>
                                <thead>
                                    <tr className="px-6 py-2 text-base font-bold text-gray-700">
                                        닉네임 : 
                                    <input type="text" readOnly value={auth.nickName}  className="px-6 py-4 text-xs text-left text-gray-700 border-2" />
                                    </tr>
        
                                    <tr className="px-40 text-base font-bold text-gray-700">
                                        가입일 : 
                                        <input type="text" readOnly value={auth.joinDate}  className="px-6 py-4 text-xs text-left text-gray-700 border-2" />
                                    </tr>

                                    <tr className="px-40 text-base font-bold text-gray-700">
                                        내가 쓴 글 :
                                        <textarea type="text" readOnly value="기타 정보"   className="px-6 py-4 text-xs text-left text-gray-700 border-2" />
                                    </tr>
                                </thead>
                            </table>
                            {/* <Link href='/board-category/ModifyBoard'>
                                <button type='modify' onClick={handleDetail} className='px-5 py-2 mx-3 font-bold border-2 rounded-lg text-neutral-900 hover:bg-neutral-700 '>수정</button>
                            </Link> */}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>
  )
}

export default UserInfo