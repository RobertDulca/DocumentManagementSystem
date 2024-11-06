<script>
    import RestClient from '../../RestClient'; // Update with actual path if different
    import { onMount } from 'svelte';
  
    let documents = [];
  
    onMount(async () => {
      try {
        documents = await RestClient.get('/api/documents');
      } catch (error) {
        console.error('Error fetching documents:', error);
      }
    });
  </script>
  
  <div class="container mt-4">
    <h1 class="text-center">Documents</h1>
    <hr>
  
    <table class="table table-striped">
      <thead class="thead-dark">
        <tr>
          <th>#</th>
          <th>Document Name</th>
          <th>Date Uploaded</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {#each documents as document, index}
          <tr>
            <th>{index + 1}</th>
            <td>{document.title}</td>
            <td>{new Date(document.created).toLocaleDateString()}</td>
            <td>
              <button class="btn btn-primary btn-sm">View</button>
              <button class="btn btn-danger btn-sm">Delete</button>
            </td>
          </tr>
        {/each}
      </tbody>
    </table>
  </div>

  