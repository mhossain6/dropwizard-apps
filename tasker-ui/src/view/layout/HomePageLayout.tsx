import Tasker from "../Tasker";
import Footer from "./Footer";
import Header from "./Header";


const HomePageLayout = (props?: any) => {
  return (
    <div className="homepage">
      <div className="header">
        <Header />
      </div>
      <Tasker name="Hello World" />
      <div className="footer">
        <Footer />
      </div>
    </div>
  );
};

export default HomePageLayout;
