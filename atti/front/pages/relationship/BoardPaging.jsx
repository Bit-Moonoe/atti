import React from 'react'

const BoardPaging = ({ total, limit, page, setPage }) => {
  const numPages = Math.ceil(total / limit);

  
  return (
    <>
      {/* <nav className='flex items-center justify-center gap-1 m-4'></nav> */}
      <div className="flex justify-center text-gray-100">
        <nav aria-label="Page navigation example">
          <ul className="flex list-style-none">

            <button onClick={() => setPage(page - 1)} disabled={page === 1}>
              &laquo;
            </button>

            {Array(numPages)
              .fill()
              .map((_, i) => (
                <button
                  key={i + 1}
                  onClick={() => setPage(i + 1)}
                  aria-current={page === i + 1 ? "page" : null}
                  className='font-bold'
                >
                  {i + 1}
                </button>
              ))}

            <button onClick={() => setPage(page + 1)} disabled={page === numPages}
              className="block text-sm hover:text-gray-700">
              {/* <div className="block text-sm hover:text-gray-700"></div> */}
              &raquo;
            </button>

          </ul>
        </nav>
      </div>



    </>
  );
}

// const Nav = styled.nav`
//   display: flex;
//   justify-content: center;
//   align-items: center;
//   gap: 4px;
//   margin: 16px;
// `;

// const Button = styled.button`
//   border: none;
//   border-radius: 8px;
//   padding: 8px;
//   margin: 0;
//   background: black;
//   color: white;
//   font-size: 1rem;

//   &:hover {
//     background: tomato;
//     cursor: pointer;
//     transform: translateY(-2px);
//   }

//   &[disabled] {
//     background: grey;
//     cursor: revert;
//     transform: revert;
//   }

//   &[aria-current] {
//     background: deeppink;
//     font-weight: bold;
//     cursor: revert;
//     transform: revert;
//   }
// `;

export default BoardPaging
