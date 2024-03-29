import React, { useEffect, useState } from "react";
import { Container, Nav, Navbar, Button } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import "../Css/Navbar.css";
import img from "../media/logo.jpg";
import { useNavigate } from "react-router-dom";
export function NavigationBar() {
  const [isLoggedIn, setLoggedIn] = useState(() => {
    // Set initial login status based on session storage
    return sessionStorage.getItem("id") || sessionStorage.getItem("owner-id");
  });

  const navigate = useNavigate();

  const handleLogout = () => {
    // Implement your logout logic here, such as clearing user session, updating state, etc.
    sessionStorage.clear(); // Clear all items from session storage
    setLoggedIn(false);
    navigate(`/`);
    // Additional logic like redirecting to the login page can be added here
  };

  useEffect(() => {
    const storageEventListener = (event) => {
      console.log("Storage event detected");
      if (event.storageArea === sessionStorage) {
        // Check login status whenever there is a change in session storage
        setLoggedIn(
          sessionStorage.getItem("id") || sessionStorage.getItem("owner-id")
        );
      }
    };

    // Listen for changes in storage (e.g., due to other tabs)
    window.addEventListener("storage", storageEventListener);

    // Cleanup event listener on component unmount
    return () => {
      window.removeEventListener("storage", storageEventListener);
    };
  }, []); // Empty dependency array to ensure this effect runs only once


  return (
    <>
      <Navbar  style={{ backgroundColor: "#D8232A" }} variant="dark" >
        <Container>
          <LinkContainer to="/">
            <img  className="hover-effect" src={img} alt="lol" height={"70px"} />
          </LinkContainer>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
              <div className="home">
              <LinkContainer class="" to="/">
                <Nav.Link>Home</Nav.Link>
              </LinkContainer>
              </div>
              <div class="dropdown nav-pad">
                <button class="dropbtn sub-nav">Login</button>

                <div class="dropdown-content">
                  <LinkContainer  to="/login-user">
                  <Nav.Link>Login User</Nav.Link>
                  </LinkContainer>
              
                  <LinkContainer to="/login-owner">
                  <Nav.Link>Login Owner</Nav.Link>
                  </LinkContainer>
                 
                  <LinkContainer to="/login-host">
                  <Nav.Link>Login host</Nav.Link>
                  </LinkContainer>
                </div>
              </div>
              <div class="nav-pad dropdown">
                <button class="dropbtn ">Register</button>

                <div class="dropdown-content">
                  <LinkContainer to="/registrationuser">
                  <Nav.Link>User Registration</Nav.Link>
                  </LinkContainer>
           
                  <LinkContainer to="/registrationowner">
                  <Nav.Link>Owner Registration</Nav.Link>
                  </LinkContainer>
                 
                </div>
              </div>

              {/* <LinkContainer to="/login">
                <Nav.Link>Login</Nav.Link>
              </LinkContainer> */}
              {/* <LinkContainer to="/registrationuser">
                <Nav.Link>Registration User</Nav.Link>
              </LinkContainer> */}
              {/* <LinkContainer to="/registrationowner">
                <Nav.Link>Registration Owner</Nav.Link>
              </LinkContainer> */}
               {/* <div className="nav-pad">
                <LinkContainer   to="/dashboard">
                 <Nav.Link style={{ display: 'inline-block' }} >Owner Section</Nav.Link>
                </LinkContainer> 
               </div>  */}
              <div className="nav-pad">
              <LinkContainer  to="/contactus">
                <Nav.Link>Contact Us</Nav.Link>
              </LinkContainer>
              </div>
              {/* <div className="nav-pad">
              <LinkContainer  to="/host">
                <Nav.Link>Host</Nav.Link>
              </LinkContainer>
              </div> */}
              {/* <div className="nav-pad ">
              <LinkContainer  to="/userview">
                <Nav.Link>User View</Nav.Link>
              </LinkContainer>
              </div> */}
              <div className="nav-pad">
              <LinkContainer  to="/aboutus">
                <Nav.Link>About Us</Nav.Link>
              </LinkContainer>
              {/* <LinkContainer  to="/properties">
                <Nav.Link>Properties</Nav.Link>
              </LinkContainer> */}
              </div>
              {/* <div className="nav-pad">
              <LinkContainer  to="/services">
                <Nav.Link>Service View</Nav.Link>
              </LinkContainer>
              </div> */}
              <Nav>
              {isLoggedIn ? (
                <Button variant="light" onClick={handleLogout}>
                  Logout
                </Button>
              ) : (
                <>
                  <LinkContainer to="/login">
                    <Nav.Link>.</Nav.Link>
                  </LinkContainer>
                  {/* Add other login-related links if needed */}
                </>
              )}
            </Nav>
              
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
}
