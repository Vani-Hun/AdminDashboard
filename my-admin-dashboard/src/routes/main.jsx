
import '../styles/Masthead.scss'
import '../styles/Common.scss'
import { Outlet, NavLink, Link, useLoaderData, Form, redirect, useNavigation, useSubmit, } from "react-router-dom";
import { Masthead } from '../components/Masthead';
import { verify } from '../utils/api'

export async function action() {

}
export async function loader() {

    try {
        const admin = await verify();
        return { admin };
    } catch (error) {
        console.error('Error: ', error);
        throw error;
    }
}
export default function Main() {
    const { admin } = useLoaderData();
    console.log("admin:", admin)

    return (
        <>
            <div className='main-container'>
                <Masthead admin={admin} />
                <Outlet />
            </div>
        </>

    );
}


