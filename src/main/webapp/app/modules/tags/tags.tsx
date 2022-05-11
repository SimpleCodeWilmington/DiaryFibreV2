import './tags.scss';

import React, { useState } from 'react';
import { useAppSelector } from 'app/config/store';
import axios from 'axios';

export const Tags = () => {
  const account = useAppSelector(state => state.authentication.account);
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

  function handleSubmit (event) {
    event.preventDefault();
    tags.forEach(function (value) {
      axios.post('/api/tags', {
        tagName: value, // or tag_name
      })
      .then(function(response) {
        // eslint-disable-next-line no-console
        console.log(response);
      })
      .catch(function(error) {
        // eslint-disable-next-line no-console
        console.log(error);
      })
    });
  };

  return (
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
      <button onClick={handleSubmit}>SUBMIT</button>
    </div>
  );
};

export default Tags;

// resource: https://blog.logrocket.com/building-a-tag-input-field-component-for-react/

// on save -> create tags, link entered tags to blog post









