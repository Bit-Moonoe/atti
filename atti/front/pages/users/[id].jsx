import { useRouter } from 'next/router'
import React from 'react'
// import UserDetail from '../../components/Users/UserDetail';
import UserInfo from './UserInfo';

const UserDetailPage = () => {
    const router = useRouter();
    console.log(router.query.id);
  return (
    <div className='container p-2 mx-auto bg-slate-200'>
    {/* <UserDetail /> */}
    <UserInfo />
    </div>
  )
}

export default UserDetailPage