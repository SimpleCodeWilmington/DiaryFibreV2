 import './tags2.scss';

 import React, { useState } from 'react';
 import { Link } from 'react-router-dom';
 import { Translate } from 'react-jhipster';
 import { Row, Col, Alert, Button } from 'reactstrap';
 import { useAppSelector } from 'app/config/store';

export const Tags2 = () => {
   const account = useAppSelector(state => state.authentication.account);

const [input, setInput] = useState('');
const [tags, setTags] = useState([]);
const [isKeyReleased, setIsKeyReleased] = useState(false);

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

  if (key === "Backspace" && !input.length && tags.length && isKeyReleased) {
    const tagsCopy = [...tags];
    const poppedTag = tagsCopy.pop();
    e.preventDefault();
    setTags(tagsCopy);
    setInput(poppedTag);
  }

  setIsKeyReleased(false);
};

const onKeyUp = () => {
  setIsKeyReleased(true);
}


return (
<div className="container">
  {tags.map((tag) => <div className="tag" key="tag">{tag}</div>)}
  <input
    value={input}
    placeholder="Enter a tag"
    onKeyDown={onKeyDown}
    onKeyUp={onKeyUp}
    onChange={onChange}
  >
  </input>
</div>
);


};
export default Tags2;
