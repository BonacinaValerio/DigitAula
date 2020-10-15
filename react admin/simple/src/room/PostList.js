import * as React from 'react';
import memoize from 'lodash/memoize';
import BookIcon from '@material-ui/icons/Book';
import {
    Datagrid,
    Filter,
    List,
    BulkDeleteWithConfirmButton,
    SearchInput,
    TextField,
    TextInput,
} from 'react-admin'; // eslint-disable-line import/no-unresolved

export const PostIcon = BookIcon;

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


const UserList = (props) => (
    <List 
        {...props} 
        bulkActionButtons={<UserBulkActionButtons />} 
        filters={<UserFilter />}>
      <Datagrid 
        rowClick={rowClick('admin')}>
        <TextField source="id" />
        <TextField source="name" />
        <TextField source="idClass" />
        <TextField source="live" />
        <TextField source="drive" />
      </Datagrid>
    </List>
  );

export default UserList;

