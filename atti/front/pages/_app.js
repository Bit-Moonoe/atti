import NavbarAfter from '../components/Layout/NavbarAfter'
import NavbarBefore from '../components/Layout/NavbarBefore'
import authAtom from '../public/stores/authAtom'
import '../styles/globals.css'
import { useAtom } from 'jotai'

function MyApp({ Component, pageProps }) {
  const [auth, setAuth] = useAtom(authAtom);
  return(
    <>
      {auth.token == null ? <NavbarBefore /> : <NavbarAfter />}
      <Component {...pageProps} />
    </>
  )
}

export default MyApp
