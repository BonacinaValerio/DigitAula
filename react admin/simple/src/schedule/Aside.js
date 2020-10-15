import * as React from 'react';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { CreateButton } from 'react-admin';

const useStyles = makeStyles(theme => ({
    root: {
        [theme.breakpoints.up('sm')]: {
            width: 200,
            marginTop: '10em',
        },
        [theme.breakpoints.down('sm')]: {
            width: 200,
            overflowX: 'hidden',
            margin: 0,
        },
    },
}));

const Aside = () => {
    return (
        <CreateButton style={{ marginTop: 24 }} basePath="/schedule"/>
    );
};

export default Aside;
