import { APP_NAME } from '@/lib/constants';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className='text-gray-600,"text-xs"'>
      <div className='p-5 flex-center' >
        {currentYear} {APP_NAME}. All Rights Reserved by Drasko Kosovic
      </div>
    </footer>
  );
};

export default Footer;
