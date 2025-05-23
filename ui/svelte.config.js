import adapter from '@sveltejs/adapter-static';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	kit: {
		// adapter-auto only supports some environments, see https://svelte.dev/docs/kit/adapter-auto for a list.
		// If your environment is not supported, or you settled on a specific environment, switch out the adapter.
		// See https://svelte.dev/docs/kit/adapters for more information about adapters.
		adapter: adapter({
            pages: 'build', // Folder to output static files (customize as needed)
            assets: 'build', // Folder to output assets
            fallback: 'index.html' // Fallback for SPA routing
        }), 
		alias: {$comp: "src/components"}
	}
};

export default config;
