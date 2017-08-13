<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\Types;

class UserModel extends PersonModel implements User {
    public function __construct() {
        parent::__construct();
    }

    /**
     * @var string
     */
    private $role;

    /**
     * @return string
     */
    public function getRole() { return $this->role; }
}
