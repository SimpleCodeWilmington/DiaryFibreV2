import './tags.scss';

import React from 'react';
import { useAppSelector } from 'app/config/store';

export const Tags = () => {
  const account = useAppSelector(state => state.authentication.account);
  const [input, setInput] = useState('');
  const [tags, setTags] = useState([]);

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
  };

  return (
    <div className="container">
    {tags.map((tag) => <div className="tag">{tag}</div>)}
    <input
      value={input}
      placeholder="Tags"
      onKeyDown={onKeyDown}
      onChange={onChange}
    >
    </input>
    </div>
  );
};

export default Tags;

// resource: https://blog.logrocket.com/building-a-tag-input-field-component-for-react/









