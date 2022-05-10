import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlogText } from 'app/shared/model/blog-text.model';
import { getEntities as getBlogTexts } from 'app/entities/blog-text/blog-text.reducer';
import { IBlog } from 'app/shared/model/blog.model';
import { getEntities as getBlogs } from 'app/entities/blog/blog.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { IBlogPost } from 'app/shared/model/blog-post.model';
import { Template } from 'app/shared/model/enumerations/template.model';
import { getEntity, updateEntity, createEntity, reset } from 'app/entities/blog-post/blog-post.reducer';

import { Tags } from 'app/modules/tags/tags'

import axios from 'axios';

export const Post = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const blogTexts = useAppSelector(state => state.blogText.entities);
  const blogs = useAppSelector(state => state.blog.entities);
  const blogPostEntity = useAppSelector(state => state.blogPost.entity);
  const loading = useAppSelector(state => state.blogPost.loading);
  const updating = useAppSelector(state => state.blogPost.updating);
  const updateSuccess = useAppSelector(state => state.blogPost.updateSuccess);
  const templateValues = Object.keys(Template);
  const [tagArray, setTagArray] = useState([]);

  useEffect(() => {
    dispatch(reset());
    dispatch(getBlogTexts({}));
    dispatch(getBlogs({}));
    dispatch(getTags({}));
  }, []);

  const saveEntity = values => {
      event.preventDefault();
      tags.forEach(function (val) {
        axios.post('/api/tags', {
          tagName: val,
        })
        .then(function(response) {
          const { data } = response;
          // tagArray.push(data.id); // data is not being added to the array, even if manual - why?
                       // const data = response.json();
                       // values.tags.add(data.id);
          // eslint-disable-next-line no-console
          console.log(response);
          return {data};
        })
        .then(function({data}) {
          // eslint-disable-next-line no-console
          console.log(data.id);
          tagArray.push(data.id);
          // setTagArray(prevState => [...prevState, data.id]);
        })
        .then(function() {
          // eslint-disable-next-line no-console
          console.log(tagArray[0]); // undefined - why is this showing first?
        })
        .catch(function(error) {
          // eslint-disable-next-line no-console
          console.log(error);
        })
      });

    setTimeout(function afterTwoSeconds() {
      values.dateTime = convertDateTimeToServer(Date());
      values.template = "THEMEREDITH";
      values.tags = tagArray;
  
      const entity = {
        ...blogPostEntity,
        ...values,
        tags: mapIdList(values.tags), // this part works
        blog: blogs.find(it => it.blogName.toString() === values.blog.toString()),
      };
  
      dispatch(createEntity(entity));
        // eslint-disable-next-line no-console
        console.log(tagArray[0]); // undefined - why is this showing first?
    }, 2000)  
  };

  // Tags
    const [input, setInput] = useState('');
    const [tags, setTags] = useState([]);
    const [isKeyReleased, setIsKeyReleased] = useState(false);

    const deleteTag = (index) => {
      setTags(prevState => prevState.filter((tag, i) => i !== index))
    };

    const onChange = (e) => {
      const { value } = e.target;
      setInput(value);
    };

    const onKeyDown = (e) => {
      const { key } = e;
      const trimmedInput = input.trim();

      if (key === 'Enter' && trimmedInput.length && !tags.includes(trimmedInput)) {
          e.preventDefault();
          setTags(prevState => [...prevState, trimmedInput]);
          setInput('');
      }

      if (key === 'Backspace' && !input.length && tags.length && isKeyReleased) {
        e.preventDefault();
        const tagsCopy = [...tags];
        const poppedTag = tagsCopy.pop();

        setTags(tagsCopy);
        setInput(poppedTag);
      }

      setIsKeyReleased(false);

    };

    const onKeyUp = (e) => {
      setIsKeyReleased(true);
    };

//   const defaultValues = () =>
//     isNew
//       ? {
//           dateTime: displayDefaultDateTime(),
//         }
//       : {
//           template: 'THEDAVID',
//           ...blogPostEntity,
//           dateTime: convertDateTimeFromServer(blogPostEntity.dateTime),
//           blogtext: blogPostEntity?.blogtext?.id,
//           blog: blogPostEntity?.blog?.id,
//           tags: blogPostEntity?.tags?.map(e => e.id.toString()),
//         };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diaryFibreApp.blogPost.home.createOrEditLabel" data-cy="BlogPostCreateUpdateHeading">
            <Translate contentKey="diaryFibreApp.blogPost.home.createOrEditLabel">Create or edit a BlogPost</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm onSubmit={saveEntity}>
              <ValidatedField id="blog-post-blog" name="blog" data-cy="blog" label={translate('diaryFibreApp.blogPost.blog')} type="select">
                <option value="" key="0" />
                {blogs
                  ? blogs.map(otherEntity => (
                      <option value={otherEntity.blogName} key={otherEntity.id}>
                        {otherEntity.blogName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('diaryFibreApp.blogPost.title')}
                id="blog-post-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('diaryFibreApp.blogPost.text')}
                id="blog-post-text"
                name="text"
                data-cy="text"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
    <div className="container">
    {tags.map((tag) => <div className="tag" key="tag">{tag}</div>)}
    <input
      value={input}
      placeholder="Tags"
      onKeyDown={onKeyDown}
      onKeyUp={onKeyUp}
      onChange={onChange}
    >
    </input>
    {tags.map((tag, index) => (
      <div className="tag" key="tag">
        {tag}
        <button onClick={() => deleteTag(index)}>x</button>
      </div>
    ))}
    </div>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blog-post" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default Post;


// import './post.scss';
//
// import React, { useState, useEffect } from 'react';
// import { Link, RouteComponentProps } from 'react-router-dom';
// import { Button, Row, Col, FormText, Alert } from 'reactstrap';
// import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
// import { mapIdList } from 'app/shared/util/entity-utils';
// import { useAppDispatch, useAppSelector } from 'app/config/store';
//
// import { IBlogText } from 'app/shared/model/blog-text.model';
// import { getEntities as getBlogTexts } from 'app/entities/blog-text/blog-text.reducer';
// import { IBlog } from 'app/shared/model/blog.model';
// import { getEntities as getBlogs } from 'app/entities/blog/blog.reducer';
// import { ITag } from 'app/shared/model/tag.model';
// import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
// import { IBlogPost } from 'app/shared/model/blog-post.model';
// import { Template } from 'app/shared/model/enumerations/template.model';
// import { getEntity, updateEntity, createEntity, reset } from 'app/entities/blog-post/blog-post.reducer';
//
// import { BlogTextAdd } from 'app/entities/blog-text/blog-text-add';
// import { Tags } from 'app/modules/tags/tags'
//
// export const Post = () => {
//   const account = useAppSelector(state => state.authentication.account);
//
//   const dispatch = useAppDispatch();
//
//   const blogTexts = useAppSelector(state => state.blogText.entities);
//   const blogs = useAppSelector(state => state.blog.entities);
//   const blogPostEntity = useAppSelector(state => state.blogPost.entity);
//   const loading = useAppSelector(state => state.blogPost.loading);
//   const templateValues = Object.keys(Template);
//
//     const [input, setInput] = useState('');
//     const [tags, setTags] = useState([]);
//     const [isKeyReleased, setIsKeyReleased] = useState(false);
//
//     const deleteTag = (index) => {
//       setTags(prevState => prevState.filter((tag, i) => i !== index))
//     };
//
//     const onChange = (e) => {
//       const { value } = e.target;
//       setInput(value);
//     };
//
//     const onKeyDown = (e) => {
//       const { key } = e;
//       const trimmedInput = input.trim();
//
//       if (key === 'Enter' && trimmedInput.length && !tags.includes(trimmedInput)) {
//           e.preventDefault();
//           setTags(prevState => [...prevState, trimmedInput]);
//           setInput('');
//       }
//
//       if (key === 'Backspace' && !input.length && tags.length && isKeyReleased) {
//         e.preventDefault();
//         const tagsCopy = [...tags];
//         const poppedTag = tagsCopy.pop();
//
//         setTags(tagsCopy);
//         setInput(poppedTag);
//       }
//
//       setIsKeyReleased(false);
//
//     };
//
//     const onKeyUp = (e) => {
//       setIsKeyReleased(true);
//     };
//
//   useEffect(() => {
//     dispatch(reset());
//     dispatch(getBlogTexts({}));
//     dispatch(getBlogs({}));
//     dispatch(getTags({}));
//   }, []);
//
//   const saveEntity = values => {
//     values.dateTime = convertDateTimeToServer(Date());
//     values.template = "THEMEREDITH";
//     values.blogtext = 1;
//     values.tags = "Meredith";
//
//     const entity = {
//       ...blogPostEntity,
//       ...values,
//       tags: mapIdList(values.tags),
//       blogtext: blogTexts.find(it => it.id.toString() === values.blogtext.toString()),
//       blog: blogs.find(it => it.id.toString() === values.blog.toString()),
//     };
//
//     dispatch(createEntity(entity));
//   };
//
//   return (
//     <Row>
//       <Col md="7" className="pad">
//         <form onSubmit={saveEntity}>
//             <select id="blog-post-blog" name="blog" data-cy="blog" style={{height: '30px', width: '659px', margin: '6px 0'}}>
//               <option value="" key="0" disabled selected>Blog</option>
//               {blogs
//                 ? blogs.map(otherEntity => (
//                     <option value={otherEntity.blogName} key={otherEntity.id}>
//                       {otherEntity.blogName}
//                     </option>
//                   ))
//                 : null}
//             </select>
//             <textarea id="blog-post-title" name="title" data-cy="title" placeholder="Title" rows={1} cols={75}>
//             </textarea>
// <div className="container">
//     {tags.map((tag) => <div className="tagClass" key="tag">{tag}</div>)}
//     <input
//       value={input}
//       placeholder="Tags"
//       onKeyDown={onKeyDown}
//       onKeyUp={onKeyUp}
//       onChange={onChange}
//       id="blog-post-tag"
//       data-cy="tag"
//       name="tags"
//     >
//     </input>
//     {tags.map((tag, index) => (
//       <div className="tag" key="tag">
//         {tag}
//         <button onClick={() => deleteTag(index)}>x</button>
//       </div>
//     ))}
//     </div>
//             <textarea id="blog-post-text" name="text" data-cy="text" placeholder="Body" rows={10} cols={75}>
//             </textarea>
//                  <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit">
//                    <FontAwesomeIcon icon="save" />
//                      &nbsp;
//                    <Translate contentKey="entity.action.save">Save</Translate>
//                  </Button>
//         </form>
//       </Col>
//       <Col md="5">
//         <div className="flex-parent jc-center" style={{ margin: '15px 0' }}>
//           <Link to="/post">
//             <Button className="simple-post" type="button">
//               <span className="black">Simple</span>
//             </Button>
//           </Link>
//           <Link to="/post-advanced">
//             <Button className="advanced-post" type="button">
//               <span className="white">Advanced</span>
//             </Button>
//           </Link>
//         </div>
//         <div className="flex-parent jc-center" style={{ margin: '15px 0' }}>
//           <h5>Upload Image</h5>
//         </div>
//         <div className="flex-parent jc-center" style={{ margin: '15px 0' }}>
//           <div className="upload-image">
//             <img src="content/images/IMAGE.png" />
//           </div>
//         </div>
//         <div className="flex-parent jc-center" style={{ margin: '15px 0' }}>
//           <Button className="save-as-draft" type="button">
//             <span>Save As Draft</span>
//           </Button>
//           <Button className="post-to-blog" type="button">
//             <span>Post</span>
//           </Button>
//         </div>
//       </Col>
//     </Row>
//   );
// };
//
// export default Post;
