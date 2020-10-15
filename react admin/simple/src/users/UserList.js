/* eslint react/jsx-key: off */
import memoize from 'lodash/memoize';
import * as React from 'react';
import {
    BulkDeleteWithConfirmButton,
    Datagrid,
    Filter,
    List,
    SearchInput,
    SimpleList,
    TextField,
    TextInput,
} from 'react-admin';

import UserEditEmbedded from './UserEditEmbedded';

const UserFilter = ({...props}) => (
    <Filter {...props}>
        <SearchInput source="q" alwaysOn />
        <TextInput source="name" />
        <TextInput source="id" />
    </Filter>
);

const UserBulkActionButtons = props => (
    <BulkDeleteWithConfirmButton {...props} />
);

const rowClick = memoize(permissions => (id, basePath, record) => {
    return permissions === 'admin'
        ? Promise.resolve('edit')
        : Promise.resolve('show');
});

/*
const UserList1 = ({ permissions, ...props }) => (
    <List
        {...props}
        filters={<UserFilter permissions={permissions} />}
        filterDefaultValues={{ role: 'user' }}
        sort={{ field: 'name', order: 'ASC' }}
        aside={<Aside />}
        bulkActionButtons={<UserBulkActionButtons />}
    >
        {useMediaQuery(theme => theme.breakpoints.down('sm')) ? (
            <SimpleList
                primaryText={record => record.name}
                secondaryText={record =>
                    permissions === 'admin' ? record.role : null
                }
            />
        ) : (
            <Datagrid
                rowClick={rowClick(permissions)}
                expand={<UserEditEmbedded />}
                optimized
            >
                <TextField source="id" />
                <TextField source="name" />
                {permissions === 'admin' && <TextField source="role" />}
            </Datagrid>
        )}
    </List>
);
*/

const UserList = (props) => (
    <List 
        {...props} 
        bulkActionButtons={<UserBulkActionButtons />} 
        filters={<UserFilter />}>
      <Datagrid 
        rowClick={rowClick('admin')}
        expand={<UserEditEmbedded />}>
        <TextField source="id" />
        <TextField source="name" />
      </Datagrid>
    </List>
  );

export default UserList;
