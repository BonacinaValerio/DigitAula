/* eslint react/jsx-key: off */
import * as React from 'react';
import { Admin, Resource } from 'react-admin'; // eslint-disable-line import/no-unresolved
import { render } from 'react-dom';
import { Route } from 'react-router-dom';

import i18nProvider from './i18nProvider';

import {emailAndPasswordAuthProvider} from "./authProvider";
import firebaseDataProvider from 'ra-data-firebase-client'
import firebase from 'firebase/app'
import 'firebase/auth'
import "firebase/database";

import CustomRouteLayout from './customRouteLayout';
import CustomRouteNoLayout from './customRouteNoLayout';
import Layout from './Layout';
import room from './room';
import work_topic from './work_topic';
import users from './users';
import schedule from './schedule';

firebase.initializeApp({
    apiKey: 'AIzaSyAlXq-nxpNwKbUfZlv02ENpzEADP9I9mG4',
    authDomain: 'uppercase---project.firebaseapp.com',
    databaseURL: 'https://uppercase---project.firebaseio.com',
    projectId: 'uppercase---project',
    storageBucket: 'uppercase---project.appspot.com',
    messagingSenderId: '289689609242',
    appId: '1:289689609242:web:ae739e4cc05e79a2619da3',
    measurementId: 'G-C5F1F1B3JW'
  })  
  
const settings = {context: '', imagekey: "images", filekey: "files"}

render(
    <Admin
        authProvider={emailAndPasswordAuthProvider(firebase)}
        dataProvider={firebaseDataProvider(firebase, settings)}
        i18nProvider={i18nProvider}
        title="Example Admin"
        layout={Layout}
        customRoutes={[
            <Route
                exact
                path="/custom"
                component={props => <CustomRouteNoLayout {...props} />}
                noLayout
            />,
            <Route
                exact
                path="/custom2"
                component={props => <CustomRouteLayout {...props} />}
            />,
        ]}
    >
        {permissions => [
            <Resource name="room" {...room} />,
            <Resource name="work_topic" {...work_topic} />,
            <Resource name="users" {...users} />,
            <Resource name="schedule" {...schedule} />,
        ]}
    </Admin>,
    document.getElementById('root')
);
