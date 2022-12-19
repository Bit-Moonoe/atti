/** @type {import('next').NextConfig} */

const nextConfig = {
  reactStrictMode: true,
}

module.exports = {
  async rewrites() {
    return [
      {
        source: "/:path*",
        destination: "http://localhost:3000/:path*",
      },
    ];
  }
}

// module.exports = {
//   experimental: {
//     swcPlugins: [
//       ["next-superjson-plugin", {}],
//     ],
//   },
// }
